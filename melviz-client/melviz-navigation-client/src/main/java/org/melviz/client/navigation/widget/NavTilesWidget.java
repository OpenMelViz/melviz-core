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

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import javax.annotation.PreDestroy;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;

import elemental2.dom.HTMLElement;
import org.jboss.errai.ioc.client.container.IOC;
import org.jboss.errai.ioc.client.container.SyncBeanManager;
import org.melviz.client.navigation.NavigationManager;
import org.melviz.client.navigation.plugin.PerspectivePluginManager;
import org.melviz.navigation.NavGroup;
import org.melviz.navigation.NavItem;
import org.melviz.navigation.layout.LayoutRecursionIssue;
import org.melviz.navigation.workbench.NavWorkbenchCtx;

/**
 * A navigation widget that displays a set of navigation items using a navigable tile based approach where
 * {@link NavGroup} instances are displayed as folders and {@link NavItem} are shown as links to a specific
 * target asset (f.i: a perspective).
 */
@Dependent
public class NavTilesWidget extends BaseNavWidget {

    public interface View extends NavWidgetView<NavTilesWidget> {

        void addTileWidget(HTMLElement tileWidget);

        void showTileContent(HTMLElement tileContent);

        void clearBreadcrumb();

        void addBreadcrumbItem(String navItemName);

        void addBreadcrumbItem(String navItemName, Runnable onClicked);

        void infiniteRecursionError(String cause);
    }

    View view;
    PerspectivePluginManager perspectivePluginManager;
    SyncBeanManager beanManager;
    Map<String, NavItemTileWidget> tilesCache;
    NavItem currentPerspectiveNavItem = null;
    Stack<NavItem> navItemStack = new Stack<>();
    Map<String, HTMLElement> perspectiveCache;

    @Inject
    public NavTilesWidget(View view,
                          NavigationManager navigationManager,
                          PerspectivePluginManager perspectivePluginManager,
                          SyncBeanManager beanManager) {
        super(view, navigationManager);
        this.view = view;
        this.perspectivePluginManager = perspectivePluginManager;
        this.beanManager = beanManager;
        tilesCache = new HashMap<>();
        perspectiveCache = new HashMap<>();
    }

    public Stack<NavItem> getNavItemStack() {
        return navItemStack;
    }

    @Override
    public void show(NavGroup navGroup) {
        this.show(navGroup, true);
    }

    public void show(List<NavItem> itemList, boolean clearBreadcrumb) {
        if (clearBreadcrumb) {
            clearBreadcrumb();
        }
        this.show(itemList);
    }

    @Override
    public void show(List<NavItem> itemList) {
        currentPerspectiveNavItem = null;
        super.show(itemList);
    }

    public void show(NavGroup navGroup, boolean clearBreadcrumb) {
        if (navGroup == null) {
            view.errorNavGroupNotFound();
        } else {
            NavGroup clone = (NavGroup) navGroup.cloneItem();
            clone.setParent(null);

            if (clearBreadcrumb) {
                clearBreadcrumb();
            }

            currentPerspectiveNavItem = null;
            super.show(clone);
        }
    }

    @Override
    protected void showItem(NavItem navItem) {
        var tileWidget = produceTile(navItem.getId());
        tileWidget.setOnClick(() -> this.openItem(navItem));
        tileWidget.show(navItem);
        view.addTileWidget(tileWidget.getElement());
    }

    @Override
    protected void showGroup(NavGroup navGroup) {
        showItem(navGroup);
    }

    public void openItem(NavItem navItem) {
        NavItem parent = navItem.getParent();
        if (navItemStack.isEmpty()) {
            if (parent != null) {
                navItemStack.add(parent);
                navItemStack.add(navItem);
            }
        } else {
            navItemStack.add(navItem);
        }
        this.updateBreadcrumb();

        if (navItem instanceof NavGroup) {
            this.show((NavGroup) navItem, false);
        } else {
            NavWorkbenchCtx navCtx = NavWorkbenchCtx.get(navItem);
            String resourceId = navCtx.getResourceId();
            if (resourceId != null) {
                openPerspective(navItem);
            }
        }
    }

    protected void openPerspective(NavItem perspectiveItem) {
        NavWorkbenchCtx navCtx = NavWorkbenchCtx.get(perspectiveItem);
        String perspectiveId = navCtx.getResourceId();
        currentPerspectiveNavItem = perspectiveItem;
        if (perspectiveCache.containsKey(perspectiveId)) {
            view.showTileContent(perspectiveCache.get(perspectiveId));
            return;
        }
        perspectivePluginManager.buildPerspectiveWidget(perspectiveId, page -> {
            perspectiveCache.put(perspectiveId, page);
            view.showTileContent(page);
        });

    }

    public void onInfiniteRecursion(LayoutRecursionIssue issue) {
        String cause = issue.printReport(navigationManager.getNavTree());
        view.infiniteRecursionError(cause);
    }

    protected void updateBreadcrumb() {
        view.clearBreadcrumb();
        for (int i = 0; i < navItemStack.size(); i++) {
            final NavItem navItem = navItemStack.get(i);
            if (i == navItemStack.size() - 1) {
                view.addBreadcrumbItem(navItem.getName());
            } else {
                view.addBreadcrumbItem(navItem.getName(), () -> gotoBreadcrumbItem(navItem));
            }
        }
    }

    public void gotoBreadcrumbItem(NavItem navItem) {
        while (navItemStack.peek() != navItem) {
            navItemStack.pop();
        }
        // Re-open the item
        if (!navItemStack.isEmpty()) {
            navItemStack.pop();
        }
        openItem(navItem);
    }

    private void clearBreadcrumb() {
        navItemStack.clear();
        updateBreadcrumb();
    }

    @Override
    public HTMLElement getElement() {
        return view.getElement();
    }

    @PreDestroy
    void destroy() {
        tilesCache.values().forEach(IOC.getBeanManager()::destroyBean);
    }

    private NavItemTileWidget produceTile(String id) {
        return tilesCache.compute(id, (key, value) -> {
            if (value != null) {
                IOC.getBeanManager().destroyBean(value);
            }
            return beanManager.lookupBean(NavItemTileWidget.class).newInstance();
        });
    }

}
