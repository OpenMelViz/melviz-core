/*
 
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.melviz.client.navigation.widget;

import org.jboss.errai.ioc.client.container.SyncBeanDef;
import org.jboss.errai.ioc.client.container.SyncBeanManager;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.melviz.client.navigation.NavigationManager;
import org.melviz.client.navigation.plugin.PerspectivePluginManager;
import org.melviz.navigation.NavItem;
import org.melviz.navigation.NavTree;
import org.melviz.navigation.impl.NavTreeBuilder;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class NavTilesWidgetTest {

    @Mock
    NavTilesWidget.View view;

    @Mock
    PerspectivePluginManager pluginManager;

    @Mock
    NavigationManager navigationManager;

    @Mock
    SyncBeanManager beanManager;

    @Mock
    SyncBeanDef<NavItemTileWidget> tileWidgetBeanDef;

    @Mock
    NavItemTileWidget tileWidget;

    NavTilesWidget presenter;
    NavTree tree;

    @Before
    public void setUp() throws Exception {
        when(beanManager.lookupBean(NavItemTileWidget.class)).thenReturn(tileWidgetBeanDef);
        when(tileWidgetBeanDef.newInstance()).thenReturn(tileWidget);
        presenter = new NavTilesWidget(view, navigationManager, pluginManager, beanManager);

        tree = new NavTreeBuilder()
                .group("Home", "Home", null, false)
                .group("A", "A", null, false)
                .item("A1", "A1", null, false)
                .item("A2", "A2", null, false)
                .group("A3", "A3", null, false)
                .item("A31", "A3", null, false)
                .build();
    }

    @Test
    public void testOpenItem() {
        NavItem navItem = tree.getItemById("A");
        navItem.setId("item");
        presenter.openItem(navItem);
        assertEquals(presenter.getNavItemStack().size(), 2);

        verify(view, times(3)).addTileWidget(tileWidget.getElement());
        verify(view).clearBreadcrumb();
        verify(view).addBreadcrumbItem(eq("Home"), any());
        verify(view).addBreadcrumbItem(eq("A"));

        reset(view);
        navItem = tree.getItemById("A3");
        presenter.openItem(navItem);
        assertEquals(presenter.getNavItemStack().size(), 3);

        verify(view, times(1)).addTileWidget(tileWidget.getElement());
        verify(view).clearBreadcrumb();
        verify(view).addBreadcrumbItem(eq("Home"), any());
        verify(view).addBreadcrumbItem(eq("A"), any());
        verify(view).addBreadcrumbItem(eq("A3"));
    }

    @Test
    public void testGotoHome() {
        NavItem navItem = tree.getItemById("A");
        presenter.openItem(navItem);
        reset(view);

        NavItem homeItem = tree.getItemById("Home");
        presenter.gotoBreadcrumbItem(homeItem);
        assertEquals(presenter.getNavItemStack().size(), 0);

        verify(view, times(1)).addTileWidget(tileWidget.getElement());
        verify(view).clearBreadcrumb();
        verify(view, never()).addBreadcrumbItem(any(), any());
    }
}
