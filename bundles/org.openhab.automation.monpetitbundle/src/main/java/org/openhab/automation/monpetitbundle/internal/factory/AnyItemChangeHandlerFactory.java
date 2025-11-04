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
package org.openhab.automation.monpetitbundle.internal.factory;

import java.util.Collection;
import java.util.Collections;

import org.eclipse.jdt.annotation.Nullable;
import org.openhab.automation.monpetitbundle.internal.handler.AnyItemChangeHandler;
import org.openhab.automation.monpetitbundle.internal.type.AnyItemTriggerType;
import org.openhab.core.automation.Module;
import org.openhab.core.automation.Trigger;
import org.openhab.core.automation.handler.BaseModuleHandlerFactory;
import org.openhab.core.automation.handler.ModuleHandler;
import org.openhab.core.automation.handler.ModuleHandlerFactory;
import org.osgi.service.component.annotations.Component;

/**
 * @author lvcv4y initial contribution
 */
@Component(service = ModuleHandlerFactory.class, immediate = true)
public class AnyItemChangeHandlerFactory extends BaseModuleHandlerFactory {

    public static final Collection<String> TYPES = Collections.singleton(AnyItemTriggerType.UID);

    @Override
    protected @Nullable ModuleHandler internalCreate(Module module, String ruleUID) {
        ModuleHandler moduleHandler = null;

        if (AnyItemTriggerType.UID.equals(module.getTypeUID())) {
            moduleHandler = new AnyItemChangeHandler((Trigger) module);
        }

        return moduleHandler;
    }

    @Override
    public Collection<String> getTypes() {
        return TYPES;
    }
}
