/*
 * Licensed under the Apache License, Version 2.0 (the "License").
 * You may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.exadel.aem.toolkit.core.handlers.editconfig;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.stream.Collectors;

import com.exadel.aem.toolkit.api.handlers.TargetBuilder;
import com.exadel.aem.toolkit.core.TargetBuilderImpl;

import com.exadel.aem.toolkit.api.annotations.editconfig.EditConfig;
import com.exadel.aem.toolkit.api.annotations.editconfig.listener.Listener;
import com.exadel.aem.toolkit.core.handlers.Handler;
import com.exadel.aem.toolkit.core.util.DialogConstants;

/**
 * {@link Handler} implementation for storing {@link Listener} arguments to {@code cq:editConfig} XML node
 */
public class ListenersHandler implements Handler, BiConsumer<TargetBuilder, EditConfig> {
    /**
     * Processes the user-defined data and writes it to XML entity
     * @param root XML element
     * @param editConfig {@code EditConfig} annotation instance
     */
    @Override
    public void accept(TargetBuilder root, EditConfig editConfig) {
        List<Listener> listeners = Arrays.asList(editConfig.listeners());
        if(listeners.isEmpty()){
            return;
        }
        Map<String, String> properties = listeners.stream()
            .collect(Collectors.toMap(Listener::event, Listener::action));
        root.appendChild(new TargetBuilderImpl(DialogConstants.NN_LISTENERS)
                .attribute(DialogConstants.PN_PRIMARY_TYPE, DialogConstants.NT_LISTENERS)
                .setAttributes(properties));
    }
}
