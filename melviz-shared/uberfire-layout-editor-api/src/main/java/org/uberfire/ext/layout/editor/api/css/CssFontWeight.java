/*
 
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.uberfire.ext.layout.editor.api.css;

/**
 * An enumeration of the CSS "font-weight" codes
 */
public enum CssFontWeight implements CssAllowedValue {

    NORMAL,
    BOLD,
    BOLDER,
    LIGHTER,
    WEIGHT_100,
    WEIGHT_200,
    WEIGHT_300,
    WEIGHT_400,
    WEIGHT_500,
    WEIGHT_600,
    WEIGHT_700,
    WEIGHT_800,
    WEIGHT_900;

    @Override
    public String getName() {
        if (name().startsWith("WEIGHT_")) {
            return name().substring(7).toLowerCase();
        }
        return this.toString().toLowerCase();
    }
}

