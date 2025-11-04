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
import org.openhab.core.automation.Trigger;
import org.openhab.core.automation.handler.BaseTriggerModuleHandler;
import org.openhab.core.events.Event;
import org.openhab.core.events.EventFilter;
import org.openhab.core.events.EventSubscriber;
import org.openhab.core.items.events.ItemStateChangedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author lvcv4y initial contribution
 */
@NonNullByDefault
public class AnyItemChangeHandler extends BaseTriggerModuleHandler implements EventSubscriber {

    private static final Logger logger = LoggerFactory.getLogger(AnyItemChangeHandler.class);

    public AnyItemChangeHandler(Trigger module) {
        super(module);
        logger.info("Creating item change handler");
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

        // Custom action
        logger.info("Item {} changed to {}", itemName, newState);
    }
}
