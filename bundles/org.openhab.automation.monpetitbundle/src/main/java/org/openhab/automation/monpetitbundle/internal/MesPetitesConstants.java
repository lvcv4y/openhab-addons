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
package org.openhab.automation.monpetitbundle.internal;

import org.eclipse.jdt.annotation.NonNullByDefault;
import org.openhab.core.thing.ThingTypeUID;

/**
 * @author Robin Lacaze - initial contribution
 */
@NonNullByDefault
public class MesPetitesConstants {
    public static final String BINDING_ID = "helloworld";

    public static final ThingTypeUID THING_TYPE_DEVICE = new ThingTypeUID(BINDING_ID, "device");

    public static final String CHANNEL_COUNTER = "counter";

    public static final String ANY_ITEM_CHANGE_UID = "org.openhab.automation.monpetitmodule.anyitemchange";
}
