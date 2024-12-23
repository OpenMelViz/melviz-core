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
package org.melviz.navigation.layout;

import java.util.ArrayList;
import java.util.List;

import org.jboss.errai.common.client.api.annotations.Portable;
import org.melviz.navigation.NavItem;
import org.melviz.navigation.NavTree;
import org.uberfire.ext.layout.editor.api.editor.LayoutTemplate;

/**
 * Class containing the list of navigation references involved in an {@link LayoutTemplate} infinite recursion issue.
 */
@Portable
public class LayoutRecursionIssue {

    List<LayoutNavigationRef> refList = new ArrayList<>();

    public LayoutRecursionIssue() {}

    public boolean contains(LayoutNavigationRef other) {
        if (other != null) {
            for (LayoutNavigationRef ref : refList) {
                if (ref.getType() != null && ref.getType().equals(other.getType()) && ref.getName() != null && ref
                        .getName().equals(other.getName())) {
                    return true;
                }
            }
        }
        return false;
    }

    public void push(LayoutNavigationRef ref) {
        refList.add(ref);
    }

    public LayoutNavigationRef pop() {
        return refList.remove(refList.size() - 1);
    }

    public boolean isEmpty() {
        return refList.isEmpty();
    }

    public List<LayoutNavigationRef> getRefList() {
        return refList;
    }

    public LayoutNavigationRef getLastDefaultItemRef() {
        for (LayoutNavigationRef ref : refList) {
            if (ref.getType().equals(LayoutNavigationRefType.DEFAULT_ITEM_FOUND) || ref.getType().equals(
                    LayoutNavigationRefType.DEFAULT_ITEM_DEFINED)) {
                return ref;
            }
        }
        return null;
    }

    public String printReport(NavTree navTree) {
        String msg = "";
        LayoutNavigationRef previousRef = null;
        for (int i = 0; i < refList.size(); i++) {
            LayoutNavigationRef navigationRef = refList.get(i);
            boolean isLast = (i == refList.size() - 1);
            String name = navigationRef.getName();

            switch (navigationRef.getType()) {

                case PERSPECTIVE:
                    String perspective = enclose(name);
                    if (previousRef == null) {
                        msg += " " + perspective;
                    } else {
                        String lastMsg = perspective;
                        switch (previousRef.getType()) {
                            case NAV_GROUP_DEFINED:
                                msg += " " + lastMsg;
                                break;
                            case DEFAULT_ITEM_DEFINED:
                                msg += " " + lastMsg;
                                msg += isLast ? "" : " " + perspective;
                                break;
                            case DEFAULT_ITEM_FOUND:
                                msg += " " + lastMsg;
                                msg += isLast ? "" : " " + perspective;
                                break;
                        }
                    }
                    break;

                case NAV_GROUP_DEFINED:
                    NavItem navGroup = navTree.getItemById(name);
                    msg += " " + enclose(navGroup.getName());
                    break;

                case NAV_GROUP_CONTEXT:
                    navGroup = navTree.getItemById(name);
                    msg += " " + enclose(navGroup.getName());
                    break;

                case NAV_COMPONENT:
                    msg += " " + enclose(name);
                    break;

                case DEFAULT_ITEM_DEFINED:
                    NavItem navItem = navTree.getItemById(name);
                    msg += " " + enclose(navItem.getName());
                    break;

                case DEFAULT_ITEM_FOUND:
                    navItem = navTree.getItemById(name);
                    msg += " " + enclose(navItem.getName());
                    break;

                default:
                    msg += " " + navigationRef.getType() + " " + navigationRef.getName();
                    break;
            }
            previousRef = navigationRef;
        }
        return msg;
    }

    private String enclose(String name) {
        return "\"" + name + "\"";
    }

}
