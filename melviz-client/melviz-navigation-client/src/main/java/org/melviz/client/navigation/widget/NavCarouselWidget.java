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

import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;

import elemental2.dom.HTMLElement;
import org.melviz.client.navigation.NavigationManager;
import org.melviz.client.navigation.plugin.PerspectivePluginManager;
import org.melviz.navigation.NavItem;
import org.melviz.navigation.layout.LayoutRecursionIssue;

@Dependent
public class NavCarouselWidget extends BaseNavWidget implements HasDefaultNavItem {

    public interface View extends NavWidgetView<NavCarouselWidget> {

        void addContentSlide(HTMLElement widget);

        void infiniteRecursionError(String cause);
    }

    View view;
    PerspectivePluginManager perspectivePluginManager;
    List<String> perspectiveIds = new ArrayList<>();
    String defaultNavItemId = null;

    @Inject
    public NavCarouselWidget(View view, NavigationManager navigationManager,
                             PerspectivePluginManager perspectivePluginManager) {
        super(view, navigationManager);
        this.view = view;
        this.perspectivePluginManager = perspectivePluginManager;
        super.setMaxLevels(1);
    }

    @Override
    public boolean areSubGroupsSupported() {
        return false;
    }

    @Override
    public String getDefaultNavItemId() {
        return defaultNavItemId;
    }

    @Override
    public void setDefaultNavItemId(String defaultNavItemId) {
        this.defaultNavItemId = defaultNavItemId;
    }

    @Override
    public void show(List<NavItem> itemList) {
        // Discard everything but runtime perspectives

        // Get the default item configured (if any)
        NavItem defaultNavItem = null;
        if (defaultNavItemId != null) {
            for (NavItem navItem : itemList) {
                if (defaultNavItemId.equals(navItem.getId())) {
                    defaultNavItem = navItem;
                }
            }
        }
        // Place the default item at the beginning of the carousel
        if (defaultNavItem != null) {
            itemList.remove(defaultNavItem);
            itemList.add(0, defaultNavItem);
        }

        perspectiveIds.clear();
        super.show(itemList);
    }

    @Override
    protected void showItem(NavItem navItem) {
        // Only runtime perspectives can be displayed
        String perspectiveId = perspectivePluginManager.getRuntimePerspectiveId(navItem);
        if (perspectiveId != null) {
            perspectiveIds.add(perspectiveId);
            perspectivePluginManager.buildPerspectiveWidget(perspectiveId, view::addContentSlide);
        }
    }

    public void onInfiniteRecursion(LayoutRecursionIssue issue) {
        String cause = issue.printReport(navigationManager.getNavTree());
        view.infiniteRecursionError(cause);
    }

    @Override
    public HTMLElement getElement() {
        return view.getElement();
    }

}
