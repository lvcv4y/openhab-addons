/**
 * Copyright (c) 2010-2022 Contributors to the openHAB project
 *
 * See the NOTICE file(s) distributed with this work for additional
 * information.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0
 *
 * SPDX-License-Identifier: EPL-2.0
 */
package org.openhab.automation.monpetitbundle.internal.subscriber;

import java.util.Collections;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

import javax.ws.rs.HttpMethod;

import org.eclipse.jdt.annotation.NonNullByDefault;
import org.eclipse.jdt.annotation.Nullable;
import org.eclipse.jetty.client.HttpClient;
import org.eclipse.jetty.client.api.ContentResponse;
import org.eclipse.jetty.client.util.StringContentProvider;
import org.openhab.core.events.Event;
import org.openhab.core.events.EventFilter;
import org.openhab.core.events.EventSubscriber;
import org.openhab.core.io.net.http.HttpClientFactory;
import org.openhab.core.items.events.ItemStateChangedEvent;
import org.openhab.core.thing.Thing;
import org.openhab.core.thing.link.ItemChannelLinkRegistry;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

/**
 * @author lvcv4y initial contribution
 */
@NonNullByDefault
@Component(immediate = true, service = EventSubscriber.class)
public class AnyItemChangeEventSubscriber implements EventSubscriber {
    private static final Logger logger = LoggerFactory.getLogger(AnyItemChangeEventSubscriber.class);
    public static final String API_URL = "http://localhost:7331/update";

    private final ItemChannelLinkRegistry itemChannelLinkRegistry;
    private final HttpClient httpClient;
    private final ObjectMapper objectMapper;

    @Activate
    public AnyItemChangeEventSubscriber(@Reference ItemChannelLinkRegistry itemChannelLinkRegistry,
            @Reference HttpClientFactory httpClientFactory) {
        this.itemChannelLinkRegistry = itemChannelLinkRegistry;
        this.httpClient = httpClientFactory.getCommonHttpClient();

        this.objectMapper = new ObjectMapper();
    }

    @Override
    public Set<String> getSubscribedEventTypes() {
        return Collections.singleton(ItemStateChangedEvent.TYPE);
    }

    @Override
    public @Nullable EventFilter getEventFilter() {
        return e -> (e instanceof ItemStateChangedEvent);
    }

    @Override
    public void receive(Event event) {
        if (!(event instanceof ItemStateChangedEvent)) // shouldn't be true but who knows
            return;

        ItemStateChangedEvent iscEvent = (ItemStateChangedEvent) event;

        String itemName = iscEvent.getItemName();
        Object newState = iscEvent.getItemState();

        for (Thing th : itemChannelLinkRegistry.getBoundThings(itemName)) {
            ObjectNode node = objectMapper.createObjectNode();
            node.put("item_name", itemName);
            node.put("thing_name", th.getLabel());
            node.put("new_state", newState.toString());

            String jsonPayload;

            try {
                jsonPayload = objectMapper.writeValueAsString(node);
            } catch (JsonProcessingException e) {
                logger.warn("Error serializing JSON: {}", e.getMessage());
                continue;
            }

            try {
                ContentResponse resp = httpClient.newRequest(API_URL).method(HttpMethod.POST)
                        .header("Content-Type", "application/json")
                        // .header("Authorization", "Bearer blabla")
                        .content(new StringContentProvider(jsonPayload)).send();

                if (resp.getStatus() >= 300) {
                    logger.warn("HTTP code is not \"OK\": Got {} for payload {} at {}", resp.getStatus(), jsonPayload,
                            API_URL);
                }
            } catch (ExecutionException | InterruptedException | TimeoutException e) {
                logger.warn("Error executing POST request: {}", e.getMessage());
            }
        }
    }
}
