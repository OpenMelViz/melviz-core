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

import java.util.function.Consumer;

import javax.enterprise.event.Event;
import javax.inject.Inject;

import elemental2.dom.DomGlobal;
import elemental2.dom.Element;
import elemental2.dom.HTMLElement;
import jsinterop.base.Js;
import org.melviz.patternfly.alert.Alert;
import org.melviz.patternfly.alert.AlertType;
import org.uberfire.ext.layout.editor.api.event.LayoutTemplateDisplayed;
import org.uberfire.ext.layout.editor.client.generator.AbstractLayoutGenerator;
import org.uberfire.mvp.Command;

public abstract class TargetDivNavWidgetView<T extends TargetDivNavWidget> extends BaseNavWidgetView<T>
                                            implements TargetDivNavWidget.View<T> {

    private static final String PF5_SELECTED_CLASS = "pf-m-current";

    private static final String MISSING_DIV = "Target div is required. Use divId to specify an existing div.";

    Alert alertBox;

    @Inject
    Event<LayoutTemplateDisplayed> layoutTemplateDisplayedEvent;

    TargetDivNavWidgetView(Alert alertBox) {
        this.alertBox = alertBox;
        alertBox.setType(AlertType.WARNING);
        alertBox.getElement().style.setProperty("width", "96%");
    }

    @Override
    public void clearContent(String targetDivId) {
        var targetDiv = getTargetDiv(targetDivId);
        if (targetDiv != null) {
            domUtil.removeAllElementChildren(targetDiv);
        }
    }

    @Override
    public void showContent(String targetDivId, elemental2.dom.HTMLElement content) {
        getTargetDiv(targetDivId, targetDiv -> {
            domUtil.removeAllElementChildren(targetDiv);
            var container = (HTMLElement) DomGlobal.document.createElement("div");
            container.style.setProperty("overflow", "hidden");
            targetDiv.appendChild(container);
            container.appendChild(content);
            layoutTemplateDisplayedEvent.fire(new LayoutTemplateDisplayed());
        }, () -> error(MISSING_DIV));
    }

    @Override
    public void errorNavGroupNotFound() {
        error(NavigationConstants.navGroupNotFound());
    }

    @Override
    public void errorNavItemsEmpty() {
        error(NavigationConstants.navGroupEmpty());
    }

    @Override
    public void infiniteRecursionError(String targetDivId, String cause) {
        var targetDiv = getTargetDiv(targetDivId);
        if (targetDiv != null) {
            domUtil.removeAllElementChildren(targetDiv);
            var message = NavigationConstants.infiniteRecursion(cause);
            alertBox.setMessage(message);
            targetDiv.appendChild(Js.cast(alertBox.getElement()));
        } else {
            error(NavigationConstants.infiniteRecursion("Unkown cause"));
        }
    }

    public void error(String message) {
        domUtil.removeAllElementChildren(navWidget);
        alertBox.setMessage(message);
        navWidget.appendChild(Js.cast(alertBox.getElement()));
    }

    protected Element getLayoutRootElement(Element el) {
        if (el == null) {
            return null;
        }
        String id = el.getAttribute("id");
        if (id != null && (id.equals(AbstractLayoutGenerator.CONTAINER_ID) || id.equals("layout"))) {
            return el;
        } else {
            return getLayoutRootElement(el.parentElement);
        }
    }

    public void getTargetDiv(String targetDivId,
                             Consumer<HTMLElement> divConsumer,
                             Command notFoundDivCallback) {
        DomGlobal.setTimeout(e -> {
            var targetDiv = getTargetDiv(targetDivId);
            if (targetDiv != null) {
                divConsumer.accept(targetDiv);
            } else {
                notFoundDivCallback.execute();
            }

        }, 1);
    }

    public HTMLElement getTargetDiv(String targetDivId) {
        HTMLElement targetDiv = null;
        if (targetDivId != null) {
            var layoutRoot = getLayoutRootElement(navWidget.parentElement);
            if (layoutRoot != null) {
                targetDiv = (HTMLElement) layoutRoot.querySelector("#" + targetDivId);
            }
        }
        return targetDiv;
    }

    protected void selectItem(elemental2.dom.Element parent, elemental2.dom.Element selected) {
        parent.childNodes.forEach((currentValue, currentIndex, listObj) -> {
            ((elemental2.dom.Element) currentValue).classList.remove(PF5_SELECTED_CLASS);
            return null;
        });
        selected.classList.add(PF5_SELECTED_CLASS);
    }
}
