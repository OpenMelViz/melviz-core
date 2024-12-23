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
package org.melviz.navigation.impl;

import org.jboss.errai.common.client.api.annotations.Portable;
import org.melviz.navigation.NavDivider;
import org.melviz.navigation.NavItem;
import org.melviz.navigation.NavItemVisitor;

@Portable
public class NavDividerImpl extends NavItemImpl implements NavDivider {

    public NavDividerImpl() {
        type = Type.DIVIDER;
    }

    @Override
    public void accept(NavItemVisitor visitor) {
        visitor.visitDivider(this);
    }

    @Override
    public NavItem cloneItem() {
        NavDividerImpl clone = new NavDividerImpl();
        clone.id = this.id;
        clone.parent = this.parent;
        clone.name = this.name;
        clone.description = this.description;
        clone.modifiable = this.modifiable;
        clone.context = this.context;
        return clone;
    }

    public String toString() {
        return super.toString("DIVIDER");
    }
}
