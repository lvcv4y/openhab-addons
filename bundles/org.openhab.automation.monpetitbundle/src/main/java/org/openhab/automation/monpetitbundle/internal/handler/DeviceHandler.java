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

import static org.openhab.automation.monpetitbundle.internal.MesPetitesConstants.CHANNEL_COUNTER;

import java.util.Collection;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import org.openhab.core.library.types.DecimalType;
import org.openhab.core.thing.ChannelUID;
import org.openhab.core.thing.Thing;
import org.openhab.core.thing.ThingStatus;
import org.openhab.core.thing.binding.BaseThingHandler;
import org.openhab.core.thing.binding.ThingHandlerService;
import org.openhab.core.types.Command;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Robin Lacaze - initial contribution
 */

public class DeviceHandler extends BaseThingHandler {
    private final Logger logger = LoggerFactory.getLogger(DeviceHandler.class);

    private volatile int counter = 0;
    private ScheduledFuture<?> job;

    public DeviceHandler(Thing thing) {
        super(thing);
    }

    @Override
    public void initialize() {
        updateStatus(ThingStatus.UNKNOWN);
        logger.info("HelloWorld: initialize()");
        // Aucune config : on passe ONLINE et on commence à publier
        updateStatus(ThingStatus.ONLINE);
        job = scheduler.scheduleWithFixedDelay(() -> {
            try {
                updateState(CHANNEL_COUNTER, new DecimalType(counter++));
            } catch (Exception e) {
                logger.warn("Publish failed", e);
            }
        }, 0, 5, TimeUnit.SECONDS);
    }

    @Override
    public void dispose() {
        if (job != null) {
            job.cancel(true);
            job = null;
        }
        logger.info("HelloWorld: dispose()");
    }

    @Override
    public void handleCommand(ChannelUID channelUID, Command command) {
    }

    @Override
    public Collection<Class<? extends ThingHandlerService>> getServices() {
        return super.getServices();
    }
}
