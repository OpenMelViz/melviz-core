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
package org.melviz.navigation;

import java.util.List;

/**
 * A {@link NavItem} that contains a list of children.
 */
public interface NavGroup extends NavItem {

    /**
     * Get the children items
     */
    List<NavItem> getChildren();

    /**
     * Change the children list
     */
    void setChildren(List<NavItem> items);
}
