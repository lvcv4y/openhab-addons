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
package org.openhab.automation.monpetitbundle.internal.handler;

import java.util.Collections;
import java.util.Set;

import org.eclipse.jdt.annotation.NonNullByDefault;
import org.eclipse.jdt.annotation.Nullable;
import org.openhab.core.events.Event;
import org.openhab.core.events.EventFilter;
import org.openhab.core.events.EventSubscriber;
import org.openhab.core.items.events.ItemStateChangedEvent;
import org.openhab.core.thing.Thing;
import org.openhab.core.thing.link.ItemChannelLinkRegistry;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author lvcv4y initial contribution
 */
@NonNullByDefault
@Component(immediate = true, service = EventSubscriber.class)
public class AnyItemChangeHandler implements EventSubscriber {

    private final ItemChannelLinkRegistry itemChannelLinkRegistry;

    @Activate
    public AnyItemChangeHandler(@Reference ItemChannelLinkRegistry itemChannelLinkRegistry) {
        this.itemChannelLinkRegistry = itemChannelLinkRegistry;
    }

    private static final Logger logger = LoggerFactory.getLogger(AnyItemChangeHandler.class);

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
            logger.info("Item {} and linked to {} changed to {}", itemName, th.getLabel(), newState);
        }
    }
}
