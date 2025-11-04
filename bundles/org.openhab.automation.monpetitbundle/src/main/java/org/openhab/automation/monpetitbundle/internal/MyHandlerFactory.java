/*
 * Copyright (c) 2010-2025 Contributors to the openHAB project
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
package org.openhab.automation.monpetitbundle.internal;

import static org.openhab.automation.monpetitbundle.internal.MesPetitesConstants.THING_TYPE_DEVICE;

import org.openhab.automation.monpetitbundle.internal.handler.DeviceHandler;
import org.openhab.core.thing.Thing;
import org.openhab.core.thing.binding.BaseThingHandlerFactory;
import org.openhab.core.thing.binding.ThingHandler;
import org.openhab.core.thing.binding.ThingHandlerFactory;
import org.osgi.service.component.annotations.Component;

/**
 * @author Robin Lacaze - initial contribution
 */

@Component(service = ThingHandlerFactory.class)
public class MyHandlerFactory extends BaseThingHandlerFactory {
    @Override
    public boolean supportsThingType(org.openhab.core.thing.ThingTypeUID thingTypeUID) {
        return THING_TYPE_DEVICE.equals(thingTypeUID);
    }

    @Override
    protected ThingHandler createHandler(Thing thing) {
        if (THING_TYPE_DEVICE.equals(thing.getThingTypeUID())) {
            return new DeviceHandler(thing);
        }
        return null;
    }
}
