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

import elemental2.dom.HTMLElement;
import org.uberfire.client.mvp.UberElemental;
import org.uberfire.mvp.Command;

public interface NavWidgetView<T> extends UberElemental<T> {

    void clearItems();

    void addGroupItem(String id, String name, String description, HTMLElement element);

    void addItem(String id, String name, String description, Command onItemSelected);

    void addDivider();

    void setSelectedItem(String id);

    void clearSelectedItem();

    default void errorNavGroupNotFound() {
        addItem("error", NavigationConstants.navGroupNotFound(), null, () -> {
        });
    }

    default void errorNavItemsEmpty() {
        addItem("error", NavigationConstants.navGroupEmpty(), null, () -> {
        });
    }

}
