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
package org.openhab.automation.monpetitbundle.internal.type;

import java.util.List;

import org.eclipse.jdt.annotation.NonNullByDefault;
import org.openhab.core.automation.Visibility;
import org.openhab.core.automation.type.Output;
import org.openhab.core.automation.type.TriggerType;
import org.openhab.core.config.core.ConfigDescriptionParameter;

/**
 * @author lvcv4y initial contribution
 */
@NonNullByDefault
public class AnyItemTriggerType extends TriggerType {
    public static final String UID = "AnyItemTriggerType";
    public static final String CHANGED_EVENT = "changedEvent";

    public static AnyItemTriggerType initialize() {
        /*
         * List<Output> outputs = new ArrayList<>();
         * Output changedEvent = new Output(CHANGED_EVENT, ItemStateChangedEvent.class.getName());
         */

        return new AnyItemTriggerType(List.of(), List.of());
    }

    public AnyItemTriggerType(List<Output> output, List<ConfigDescriptionParameter> config) {
        super(UID, config, "Any Item Trigger", "This triggers when any item changes state", null, Visibility.VISIBLE,
                output);
    }
}
