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
package com.exadel.aem.toolkit.core.handlers.widget;

import com.exadel.aem.toolkit.api.annotations.meta.ResourceType;
import com.exadel.aem.toolkit.api.annotations.widgets.autocomplete.Autocomplete;
import com.exadel.aem.toolkit.api.handlers.SourceFacade;
import com.exadel.aem.toolkit.api.handlers.TargetBuilder;
import com.exadel.aem.toolkit.core.TargetBuilderImpl;
import com.exadel.aem.toolkit.core.handlers.Handler;
import com.exadel.aem.toolkit.core.util.DialogConstants;

import java.util.function.BiConsumer;

/**
 * {@link Handler} implementation used to create markup responsible for Granite UI {@code Multifield} widget functionality
 * within the {@code cq:dialog} XML node
 */
class AutocompleteHandler implements Handler, BiConsumer<SourceFacade, TargetBuilder> {
    /**
     * Processes the user-defined data and writes it to XML entity
     *
     * @param source Current {@code SourceFacade} instance
     * @param target Current {@code TargetFacade} instance
     */
    @Override
    public void accept(SourceFacade source, TargetBuilder target) {
        Autocomplete autocomplete = source.adaptTo(Autocomplete.class);
        TargetBuilder datasource = new TargetBuilderImpl(DialogConstants.NN_DATASOURCE)
                .attribute(DialogConstants.PN_SLING_RESOURCE_TYPE, autocomplete.datasource().annotationType().getAnnotation(ResourceType.class).value());
        datasource.mapProperties(autocomplete.datasource());
        target.appendChild(datasource);

        TargetBuilder options = new TargetBuilderImpl(DialogConstants.NN_OPTIONS)
                .attribute(DialogConstants.PN_SLING_RESOURCE_TYPE, autocomplete.datasource().annotationType().getAnnotation(ResourceType.class).value());
        datasource.mapProperties(autocomplete.options());
        target.appendChild(options);

        TargetBuilder values = new TargetBuilderImpl(DialogConstants.NN_VALUES)
                .attribute(DialogConstants.PN_SLING_RESOURCE_TYPE, autocomplete.datasource().annotationType().getAnnotation(ResourceType.class).value());
        values.mapProperties(autocomplete.values());
        target.appendChild(values);
    }
}
