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

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import elemental2.core.Global;
import elemental2.dom.DomGlobal;
import org.melviz.client.parser.yaml.JsYaml;
import org.melviz.client.parser.yaml.JsYamlInjector;
import org.melviz.shared.marshalling.RuntimeModelJSONMarshaller;
import org.melviz.shared.model.RuntimeModel;

@ApplicationScoped
public class YAMLRuntimeModelClientParser implements RuntimeModelClientParser {

    @Inject
    JSONRuntimeModelClientParser jsonParser;

    @Inject
    PropertyReplacementService replacementService;

    @PostConstruct
    public void setup() {
        JsYamlInjector.ensureJsYamlInjected();
    }

    @Override
    public RuntimeModel parse(String content) {
        var jsonContent = convertToJson(content);
        var jsonMarshaller = RuntimeModelJSONMarshaller.get();
        var properties = jsonMarshaller.retrieveProperties(jsonContent);
        var allowUrlProperties = jsonMarshaller.retrieveGlobalSettings(jsonContent).isAllowUrlProperties();

        if (properties != null && !properties.isEmpty()) {
            var replacedContent = replacementService.replace(content, properties, allowUrlProperties);
            jsonContent = convertToJson(replacedContent);
        }
        return jsonParser.parse(jsonContent);
    }

    @Override
    public boolean test(String content) {
        try {
            convertToJson(content);
            return true;
        } catch (Exception e) {
            DomGlobal.console.debug("Not YAML Content");
            DomGlobal.console.debug(e);
        }
        return false;
    }

    private String convertToJson(String content) {
        var object = JsYaml.Builder.get().load(content);
        return Global.JSON.stringify(object);
    }
}
