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

import elemental2.dom.HTMLAnchorElement;
import elemental2.dom.HTMLDivElement;
import elemental2.dom.HTMLElement;
import org.jboss.errai.common.client.dom.elemental2.Elemental2DomUtil;
import org.jboss.errai.ui.shared.api.annotations.DataField;
import org.jboss.errai.ui.shared.api.annotations.Templated;

@Templated
public class FilterLabelSetView implements FilterLabelSet.View {

    private static final String CLEAR_ALL = "Clear All";

    @Inject
    @DataField
    HTMLDivElement mainDiv;

    @Inject
    @DataField
    HTMLDivElement clearAllContainer;

    @Inject
    @DataField
    HTMLAnchorElement clearAll;

    @Inject
    Elemental2DomUtil domUtil;

    FilterLabelSet presenter;

    @Override
    public void init(FilterLabelSet presenter) {
        this.presenter = presenter;
        clearAll.textContent = CLEAR_ALL;
        clearAll.onclick = e -> {
            presenter.onClearAll();
            return null;
        };
    }

    @Override
    public void clearAll() {
        domUtil.removeAllElementChildren(mainDiv);
        mainDiv.appendChild(clearAllContainer);
    }

    @Override
    public void setClearAllEnabled(boolean enabled) {
        clearAll.style.display = enabled ? "block" : "none";
    }

    @Override
    public void addLabel(FilterLabel label) {
        mainDiv.insertBefore(label.getElement(), clearAllContainer);
    }

    @Override
    public HTMLElement getElement() {
        return mainDiv;
    }
}
