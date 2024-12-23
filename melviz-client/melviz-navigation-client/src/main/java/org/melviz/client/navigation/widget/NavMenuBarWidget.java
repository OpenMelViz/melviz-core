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
package org.melviz.client.navigation.widget;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;

import elemental2.dom.HTMLElement;
import org.jboss.errai.ioc.client.container.SyncBeanManager;
import org.melviz.client.navigation.NavigationManager;
import org.melviz.client.navigation.plugin.PerspectivePluginManager;

@Dependent
public class NavMenuBarWidget extends TargetDivNavWidget {

    public interface View extends TargetDivNavWidget.View<NavMenuBarWidget> {

        void setNavHeaderVisible(boolean visible);
    }

    View view;
    SyncBeanManager beanManager;

    @Inject
    public NavMenuBarWidget(View view,
                            SyncBeanManager beanManager,
                            PerspectivePluginManager pluginManager,
                            NavigationManager navigationManager) {
        super(view, pluginManager, navigationManager);
        this.view = view;
        this.beanManager = beanManager;
    }

    @Override
    public NavWidget lookupNavGroupWidget() {
        return beanManager.lookupBean(NavDropDownWidget.class).newInstance();
    }

    public void setNavHeaderVisible(boolean visible) {
        view.setNavHeaderVisible(visible);
    }

    @Override
    public HTMLElement getElement() {
        return view.getElement();
    }
}
