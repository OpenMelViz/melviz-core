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
package org.melviz.patternfly.title;

import java.util.stream.Stream;

public enum TitleSize {

    XL4("4xl"),
    XL3("3xl"),
    XL2("2xl"),
    XL("xl"),
    LG("lg"),
    MD("md");

    String size;

    TitleSize(String size) {
        this.size = size;
    }

    public static TitleSize fromSize(String sizeStr) {
        return Stream.of(TitleSize.values())
                .filter(s -> s.size.equals(sizeStr))
                .findFirst()
                .orElse(TitleSize.XL);
    }

    public String getSize() {
        return size;
    }

    public String toCssClass() {
        return "pf-m-" + size;
    }

}
