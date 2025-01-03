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

import java.util.List;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;

import elemental2.dom.HTMLElement;
import org.jboss.errai.ioc.client.container.SyncBeanManager;
import org.melviz.client.navigation.NavigationManager;
import org.melviz.client.navigation.plugin.PerspectivePluginManager;
import org.melviz.navigation.NavItem;
import org.uberfire.mvp.Command;

@Dependent
public class NavTreeWidget extends TargetDivNavWidget {

    public interface View extends TargetDivNavWidget.View<NavTreeWidget> {

        void setLevel(int level);

        void addRuntimePerspective(String id, String name, String description, Command onClicked);

        void addPerspective(String id, String name, String description, Command onClicked);
    }

    View view;
    SyncBeanManager beanManager;

    @Inject
    public NavTreeWidget(View view,
                         SyncBeanManager beanManager,
                         PerspectivePluginManager pluginManager,
                         NavigationManager navigationManager) {
        super(view, pluginManager, navigationManager);
        this.view = view;
        this.beanManager = beanManager;
    }

    @Override
    protected NavWidget lookupNavGroupWidget() {
        return beanManager.lookupBean(NavTreeWidget.class).newInstance();
    }

    @Override
    public void show(List<NavItem> itemList) {
        view.setLevel(getLevel());
        super.show(itemList);
    }

    @Override
    protected void showItem(NavItem navItem) {
        view.addRuntimePerspective(navItem.getId(), navItem.getName(), navItem.getDescription(),
                () -> onItemClicked(navItem));
    }

    @Override
    public HTMLElement getElement() {
        return view.getElement();
    }
}
