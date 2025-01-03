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

package org.melviz.displayer.external;

import java.util.Map;

import elemental2.core.JsMap;
import jsinterop.annotations.JsOverlay;
import jsinterop.annotations.JsPackage;
import jsinterop.annotations.JsType;

@JsType(isNative = true, namespace = JsPackage.GLOBAL, name = "Object")
public class ExternalComponentMessage {

    JsMap<String, Object> properties;

    // Change this when @JsEnum is supported.
    String type;

    @JsOverlay
    static ExternalComponentMessage create(String messageType, Map<String, Object> properties) {
        ExternalComponentMessage message = new ExternalComponentMessage();
        message.properties = new JsMap<>();
        message.type = messageType;
        properties.forEach(message::setProperty);
        return message;
    }

    @JsOverlay
    public final void setProperty(String key, Object value) {
        properties.set(key, value);
    }

    @JsOverlay
    public final Object getProperty(String key) {
        return properties != null ? properties.get(key) : null;
    }

    @JsOverlay
    public final String getType() {
        return type;
    }

}