/*
 
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.melviz.client.parser;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.melviz.displayer.GlobalDisplayerSettings;
import org.melviz.shared.marshalling.RuntimeModelJSONMarshaller;
import org.melviz.shared.model.RuntimeModel;

@ApplicationScoped
public class JSONRuntimeModelClientParser implements RuntimeModelClientParser {

    @Inject
    PropertyReplacementService replaceService;

    @Inject
    GlobalDisplayerSettings globalDisplayerSettings;

    @Override
    public RuntimeModel parse(String jsonContent) {
        var runtimeModelJsonMarshaller = RuntimeModelJSONMarshaller.get();
        var allowUrlProperties = runtimeModelJsonMarshaller.retrieveGlobalSettings(jsonContent).isAllowUrlProperties();

        var properties = runtimeModelJsonMarshaller.retrieveProperties(jsonContent);
        var newContent = replaceService.replace(jsonContent, properties, allowUrlProperties);
        return runtimeModelJsonMarshaller.fromJson(newContent);
    }

    @Override
    public boolean test(String content) {
        return content.trim().startsWith("{");
    }

}
