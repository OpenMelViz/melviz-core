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

import jsinterop.annotations.JsOverlay;
import jsinterop.annotations.JsPackage;
import jsinterop.annotations.JsProperty;
import jsinterop.annotations.JsType;

@JsType(isNative = true, namespace = JsPackage.GLOBAL, name = "Object")
public class ExternalColumnSettings {

    @JsProperty
    String columnId;
    @JsProperty
    String columnName;
    @JsProperty
    String valueExpression;
    @JsProperty
    String emptyTemplate;
    @JsProperty
    String valuePattern;

    @JsOverlay
    public static ExternalColumnSettings of(String columnId,
                                            String columnName,
                                            String valueExpression,
                                            String emptyTemplate,
                                            String valuePattern) {
        ExternalColumnSettings settings = new ExternalColumnSettings();
        settings.columnId = columnId;
        settings.columnName = columnName;
        settings.valueExpression = valueExpression;
        settings.emptyTemplate = emptyTemplate;
        settings.valuePattern = valuePattern;
        return settings;
    }

}