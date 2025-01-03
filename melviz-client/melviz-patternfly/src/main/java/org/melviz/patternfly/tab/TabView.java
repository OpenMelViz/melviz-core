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
package org.melviz.patternfly.tab;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.inject.Named;

import elemental2.dom.HTMLButtonElement;
import elemental2.dom.HTMLElement;
import elemental2.dom.HTMLLIElement;
import org.jboss.errai.common.client.dom.elemental2.Elemental2DomUtil;
import org.jboss.errai.ui.shared.api.annotations.DataField;
import org.jboss.errai.ui.shared.api.annotations.Templated;

@Dependent
@Templated
public class TabView implements Tab.View {

    private Tab presenter;

    @Inject
    @DataField
    HTMLLIElement tabItem;

    @Inject
    @DataField
    HTMLButtonElement tabButton;

    @Inject
    @DataField
    @Named("span")
    HTMLElement tabTitle;

    @Inject
    Elemental2DomUtil util;

    @Override
    public void init(Tab presenter) {
        this.presenter = presenter;
        tabButton.onclick = e -> {
            presenter.onTabClick();
            return null;
        };
    }

    @Override
    public HTMLElement getElement() {
        return tabItem;
    }

    public void setTitle(String title) {
        tabTitle.textContent = title;
    }

}
