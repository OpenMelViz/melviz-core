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
package org.melviz.client.navigation.layout.editor;

import java.util.Collections;
import java.util.Map;

import elemental2.dom.HTMLElement;
import jsinterop.base.Js;
import org.melviz.client.navigation.NavigationManager;
import org.melviz.client.navigation.plugin.PerspectivePluginManager;
import org.melviz.client.navigation.widget.HasDefaultNavItem;
import org.melviz.client.navigation.widget.HasTargetDiv;
import org.melviz.client.navigation.widget.NavWidget;
import org.melviz.navigation.NavGroup;
import org.uberfire.ext.layout.editor.api.editor.LayoutTemplate;
import org.uberfire.ext.layout.editor.client.api.RenderingContext;

import static org.melviz.navigation.layout.NavDragComponentSettings.NAV_DEFAULT_ID;
import static org.melviz.navigation.layout.NavDragComponentSettings.NAV_GROUP_ID;
import static org.melviz.navigation.layout.NavDragComponentSettings.TARGET_DIV_ID;

public abstract class AbstractNavDragComponent implements NavDragComponent {

    NavigationManager navigationManager;
    PerspectivePluginManager pluginManager;
    NavWidget navWidget;
    String navGroupId = null;
    LayoutTemplate layoutTemplate;

    public AbstractNavDragComponent() {}

    public AbstractNavDragComponent(NavigationManager navigationManager,
                                    PerspectivePluginManager pluginManager,
                                    NavWidget navWidget) {
        this.navigationManager = navigationManager;
        this.pluginManager = pluginManager;
        this.navWidget = navWidget;
        this.navWidget.setOnStaleCommand(this::showNavWidget);
    }

    @Override
    public NavWidget getNavWidget() {
        return navWidget;
    }

    @Override
    public HTMLElement getShowWidget(RenderingContext ctx) {
        Map<String, String> properties = ctx.getComponent().getProperties();

        // Nav group settings
        navGroupId = properties.get(NAV_GROUP_ID);
        navWidget.setHideEmptyGroups(true);

        // Default item settings
        if (navWidget instanceof HasDefaultNavItem) {
            String navItemId = properties.get(NAV_DEFAULT_ID);
            ((HasDefaultNavItem) navWidget).setDefaultNavItemId(navItemId);
        }
        // Target div settings
        if (navWidget instanceof HasTargetDiv) {
            String targetDivId = properties.get(TARGET_DIV_ID);
            ((HasTargetDiv) navWidget).setTargetDivId(targetDivId);
            ((HasTargetDiv) navWidget).setGotoItemEnabled(true);
        }
        this.showNavWidget();
        return Js.cast(navWidget.getElement());
    }

    @Override
    public void dispose() {
        navWidget.dispose();
    }

    protected void showNavWidget() {
        if (navGroupId != null) {
            NavGroup navGroup = (NavGroup) navigationManager.getNavTree().getItemById(navGroupId);
            navWidget.show(navGroup);
        } else {
            navWidget.show(Collections.emptyList());
        }
    }

}
