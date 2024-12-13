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

import elemental2.core.JsArray;
import jsinterop.annotations.JsOverlay;
import jsinterop.annotations.JsPackage;
import jsinterop.annotations.JsProperty;
import jsinterop.annotations.JsType;

/**
 * DataSet representation for external Components.
 *
 */
@JsType(isNative = true, namespace = JsPackage.GLOBAL, name = "Object")
public class ExternalDataSet {

    @JsProperty
    JsArray<ExternalColumn> columns;

    @JsProperty
    JsArray<JsArray<String>> data;

    @JsOverlay
    public static ExternalDataSet of(ExternalColumn[] columns, String[][] data) {
        ExternalDataSet ds = new ExternalDataSet();
        ds.columns = new JsArray<>();
        ds.data = new JsArray<>();

        for(ExternalColumn column : columns) {
            ds.columns.push(column);
        }

        for (String[] line: data) {
            JsArray<String> lineArray = new JsArray<>();
            for (String v : line) {
                lineArray.push(v);
            }
            ds.data.push(lineArray);
        }         
        return ds;
    }

}