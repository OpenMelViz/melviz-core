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

import elemental2.dom.HTMLElement;
import org.jboss.errai.common.client.api.elemental2.IsElement;
import org.melviz.navigation.NavGroup;
import org.melviz.navigation.NavItem;
import org.melviz.navigation.NavTree;
import org.uberfire.mvp.Command;

/**
 * Interface for widgets in charge of the display of navigation items
 */
public interface NavWidget extends IsElement {

    void hide();

    void show(NavTree navTree);

    void show(NavGroup navGroup);

    void show(List<NavItem> itemList);

    void setSecure(boolean secure);

    void setHideEmptyGroups(boolean hide);

    void setOnItemSelectedCommand(Command onItemSelected);

    void setOnStaleCommand(Command onStaleStatusCommand);

    NavWidget getParent();

    void setParent(NavWidget parent);

    int getLevel();

    int getMaxLevels();

    void setMaxLevels(int maxLevels);

    NavGroup getNavGroup();

    NavItem getItemSelected();

    boolean setSelectedItem(String id);

    void clearSelectedItem();

    void dispose();

    HTMLElement getElement();

}
