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
package org.melviz.client.navigation.impl;

import java.util.Optional;

import javax.enterprise.context.ApplicationScoped;

import org.melviz.client.navigation.NavigationManager;
import org.melviz.navigation.NavTree;

@ApplicationScoped
public class NavigationManagerImpl implements NavigationManager {

    private NavTree navTree;
    private NavTree defaultNavTree;

    @Override
    public NavTree getDefaultNavTree() {
        return defaultNavTree;
    }

    @Override
    public void setDefaultNavTree(NavTree defaultNavTree) {
        this.defaultNavTree = defaultNavTree;
    }

    @Override
    public NavTree getNavTree() {
        return !hasNavTree() ? defaultNavTree : navTree;
    }

    @Override
    public boolean hasNavTree() {
        return Optional.ofNullable(navTree).isPresent();
    }

    @Override
    public void update(NavTree navTree) {
        this.defaultNavTree = navTree;
    }

}
