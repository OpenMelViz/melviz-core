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
package org.melviz.common.client.widgets;

import javax.inject.Inject;
import javax.inject.Named;

import elemental2.dom.HTMLButtonElement;
import elemental2.dom.HTMLDivElement;
import elemental2.dom.HTMLElement;
import org.jboss.errai.ui.shared.api.annotations.DataField;
import org.jboss.errai.ui.shared.api.annotations.Templated;

@Templated
public class FilterLabelView implements FilterLabel.View {

    @Inject
    @DataField
    HTMLDivElement lblRoot;

    @Inject
    @DataField
    @Named("span")
    HTMLElement labelText;

    @Inject
    @DataField
    HTMLButtonElement labelRemove;

    FilterLabel presenter;

    @Override
    public void init(FilterLabel presenter) {
        this.presenter = presenter;
        labelRemove.onclick = e -> {
            presenter.onRemove();
            return null;
        };
    }

    @Override
    public void setLabel(String label) {
        labelText.textContent = label;
    }

    @Override
    public HTMLElement getElement() {
        return lblRoot;
    }

}
