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
import org.melviz.navigation.NavTree;
import org.melviz.navigation.impl.NavTreeBuilder;
import org.melviz.navigation.workbench.NavWorkbenchCtx;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class NavTabListWidgetTest {

    @Mock
    NavTabListWidget.View view;

    @Mock
    NavTabListWidget.View viewAdmin;

    @Mock
    NavTabListWidget.View viewDashboards;

    @Mock
    SyncBeanDef<NavTabListWidget> tablistBean;

    @Mock
    PerspectivePluginManager pluginManager;

    @Mock
    NavigationManager navigationManager;

    @Mock
    SyncBeanManager beanManager;

    NavTabListWidget tabsAdmin;
    NavTabListWidget tabsDashboards;
    NavTabListWidget presenter;
    NavTree tree;

    public static final String ITEM_ID_HOME = "home";
    public static final String ITEM_ID_GALLERY = "gallery";
    public static final String ITEM_ID_ADMIN = "admin";
    public static final String ITEM_ID_DATASETS = "datasets";
    public static final String ITEM_ID_CONTENTMGMT = "contentmgmt";
    public static final String ITEM_ID_DASHBOARDS = "dashboards";
    public static final String ITEM_ID_DASHBOARD1 = "dashboard1";
    public static final String ITEM_ID_DASHBOARD2 = "dashboard2";

    @Before
    public void setUp() throws Exception {
        tabsAdmin = new NavTabListWidget(viewAdmin, beanManager, pluginManager, navigationManager);
        tabsDashboards = new NavTabListWidget(viewDashboards, beanManager, pluginManager, navigationManager);
        presenter = new NavTabListWidget(view, beanManager, pluginManager, navigationManager);
        presenter.setGotoItemEnabled(true);

        tree = new NavTreeBuilder()
                .item(ITEM_ID_HOME, "Home", null, false, NavWorkbenchCtx.perspective(ITEM_ID_HOME))
                .item(ITEM_ID_GALLERY, "Gallery", null, false, NavWorkbenchCtx.perspective(ITEM_ID_GALLERY))
                .group(ITEM_ID_ADMIN, "Administration", null, false)
                .item(ITEM_ID_DATASETS, "Datasets", null, false, NavWorkbenchCtx.perspective(ITEM_ID_DATASETS))
                .item(ITEM_ID_CONTENTMGMT, "Content Manager", null, false, NavWorkbenchCtx.perspective(
                        ITEM_ID_CONTENTMGMT))
                .endGroup()
                .group(ITEM_ID_DASHBOARDS, "Dashboards", null, false)
                .item(ITEM_ID_DASHBOARD1, "Dashboard 1", null, false, NavWorkbenchCtx.perspective(ITEM_ID_DASHBOARD1))
                .item(ITEM_ID_DASHBOARD2, "Dashboard 2", null, false, NavWorkbenchCtx.perspective(ITEM_ID_DASHBOARD2))
                .endGroup()
                .build();

        when(beanManager.lookupBean(NavTabListWidget.class)).thenReturn(tablistBean);
        when(tablistBean.newInstance()).thenReturn(tabsAdmin, tabsDashboards);
    }

    @Test
    public void testShow() {
        presenter.show(tree);
        assertEquals(presenter.getItemSelected(), tree.getItemById(ITEM_ID_HOME));

        verify(view).init(presenter);
        verify(view).addItem(eq(ITEM_ID_HOME), anyString(), any(), any());
        verify(view).addItem(eq(ITEM_ID_GALLERY), anyString(), any(), any());
        verify(view).setSelectedItem(ITEM_ID_HOME);

        verify(viewAdmin).showAsSubmenu(true);
        verify(viewAdmin, never()).showChildrenTabs(any());
        verify(viewAdmin).addItem(eq(ITEM_ID_DATASETS), anyString(), any(), any());
        verify(viewAdmin).addItem(eq(ITEM_ID_CONTENTMGMT), anyString(), any(), any());

        verify(viewDashboards).showAsSubmenu(true);
        verify(viewDashboards, never()).showChildrenTabs(any());
        verify(viewDashboards).addItem(eq(ITEM_ID_DASHBOARD1), anyString(), any(), any());
        verify(viewDashboards).addItem(eq(ITEM_ID_DASHBOARD2), anyString(), any(), any());
    }

    @Test
    public void testDefaultNestedItem() {
        presenter.setDefaultNavItemId(ITEM_ID_DASHBOARD2);
        presenter.show(tree);

        assertEquals(presenter.getItemSelected(), tree.getItemById(ITEM_ID_DASHBOARD2));
        assertEquals(tabsDashboards.getItemSelected(), tree.getItemById(ITEM_ID_DASHBOARD2));
        assertNull(tabsAdmin.getItemSelected());

        verify(view, atLeastOnce()).clearSelectedItem();
        verify(view, atLeastOnce()).setSelectedItem(ITEM_ID_DASHBOARDS);
        verify(view).showChildrenTabs(tabsDashboards.getElement());
        verify(viewDashboards).setSelectedItem(ITEM_ID_DASHBOARD2);
        verify(viewAdmin, never()).showChildrenTabs(any());
    }

    @Test
    public void testSelectNestedItem() {
        presenter.show(tree);
        reset(view, viewAdmin, viewDashboards);

        presenter.setSelectedItem(ITEM_ID_CONTENTMGMT);
        assertEquals(presenter.getItemSelected(), tree.getItemById(ITEM_ID_CONTENTMGMT));
        assertEquals(tabsAdmin.getItemSelected(), tree.getItemById(ITEM_ID_CONTENTMGMT));
        assertNull(tabsDashboards.getItemSelected());

        verify(view).clearSelectedItem();
        verify(view).showChildrenTabs(any());
        verify(viewAdmin).setSelectedItem(ITEM_ID_CONTENTMGMT);
        verify(viewDashboards, never()).showChildrenTabs(any());
    }

    @Test
    public void testSwitchFromNestedToRoot() {
        presenter.show(tree);
        presenter.setSelectedItem(ITEM_ID_CONTENTMGMT);
        reset(view, viewAdmin, viewDashboards);

        presenter.onItemClicked(tree.getItemById(ITEM_ID_HOME));
        assertEquals(presenter.getItemSelected(), tree.getItemById(ITEM_ID_HOME));
        assertNull(tabsAdmin.getItemSelected());
        assertNull(tabsDashboards.getItemSelected());

        verify(view).clearSelectedItem();
        verify(view).setSelectedItem(ITEM_ID_HOME);
        verify(view, never()).showChildrenTabs(any());
        verify(viewAdmin, never()).showChildrenTabs(any());
        verify(viewDashboards, never()).showChildrenTabs(any());
    }
}
