/*
 
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
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
package org.melviz.patternfly.busyindicator;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import elemental2.dom.HTMLDivElement;
import elemental2.dom.HTMLElement;
import elemental2.dom.HTMLParagraphElement;
import org.jboss.errai.common.client.dom.elemental2.Elemental2DomUtil;
import org.jboss.errai.ui.shared.api.annotations.DataField;

@ApplicationScoped
public class BusyIndicator {

    @Inject
    Elemental2DomUtil util;

    @Inject
    @DataField
    HTMLDivElement busyIndicatorRoot;

    @Inject
    @DataField
    HTMLParagraphElement loadingText;

    @Inject
    @DataField
    HTMLDivElement loadingTextContainer;

    public HTMLElement getElement() {
        return busyIndicatorRoot;
    }

    public void show(String text) {
        busyIndicatorRoot.style.display = "block";
        if (text != null) {
            loadingText.textContent = text;
            loadingTextContainer.style.display = "block";
        } else {
            loadingTextContainer.style.display = "none";
        }

    }

    public void hide() {
        busyIndicatorRoot.style.display = "none";
    }
}
