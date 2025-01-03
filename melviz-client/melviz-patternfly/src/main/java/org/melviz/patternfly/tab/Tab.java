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

import javax.annotation.PostConstruct;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;

import elemental2.dom.Element;
import org.uberfire.client.mvp.UberElemental;

@Dependent
public class Tab {

    @Inject
    View view;
    private Runnable selectAction;

    public interface View extends UberElemental<Tab> {

        void setTitle(String title);
    }

    @PostConstruct
    public void init() {
        view.init(this);
    }

    public void setOnSelect(Runnable action) {
        this.selectAction = action;

    }

    public Element getElement() {
        return view.getElement();
    }

    public void onTabClick() {
        if (selectAction != null) {
            selectAction.run();
        }
    }

    public void setTitle(String title) {
        this.view.setTitle(title);
    }

}