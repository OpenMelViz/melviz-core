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
package org.melviz.displayer.client;

import javax.inject.Inject;

import elemental2.dom.DomGlobal;
import elemental2.dom.HTMLElement;
import org.jboss.errai.common.client.dom.elemental2.Elemental2DomUtil;
import org.melviz.common.client.error.ClientRuntimeError;
import org.melviz.displayer.client.resources.i18n.CommonConstants;
import org.melviz.displayer.client.resources.i18n.DisplayerConstants;
import org.melviz.patternfly.label.Label;

public abstract class AbstractDisplayerView<P extends AbstractDisplayer>
                                           implements AbstractDisplayer.View<P> {

    private HTMLElement rootPanel;
    @Inject
    private Label label = new Label();
    private HTMLElement visualization = null;
    private double refreshTimerID = -1;
    protected P presenter;

    @Inject
    protected Elemental2DomUtil domUtil;

    @Override
    public void init(P presenter) {
        this.presenter = presenter;
        rootPanel = (HTMLElement) DomGlobal.document.createElement("div");

    }

    public P getPresenter() {
        return presenter;
    }

    public void setVisualization(HTMLElement widget) {
        visualization = widget;
    }

    @Override
    public void setId(String id) {
        rootPanel.id = id;
    }

    @Override
    public void clear() {
        DomGlobal.clearInterval(refreshTimerID);
        domUtil.removeAllElementChildren(rootPanel);
    }

    @Override
    public void showLoading() {
        displayMessage(DisplayerConstants.initializing());
    }

    @Override
    public void showVisualization() {
        if (visualization != null) {
            this.clear();
            rootPanel.appendChild(visualization);
        }
    }

    @Override
    public void errorMissingSettings() {
        displayMessage(DisplayerConstants.error() + DisplayerConstants.error_settings_unset());
    }

    @Override
    public void errorMissingHandler() {
        displayMessage(DisplayerConstants.error() + DisplayerConstants.error_handler_unset());
    }

    @Override
    public void errorDataSetNotFound(String dataSetUUID) {
        displayMessage(CommonConstants.dataset_lookup_dataset_notfound(dataSetUUID));
    }

    @Override
    public void error(ClientRuntimeError e) {
        displayMessage(DisplayerConstants.error() + e.getMessage());

        if (e.getThrowable() != null) {
            DomGlobal.console.log(e.getMessage(), e.getThrowable());
        } else {
            DomGlobal.console.log(e.getMessage());
        }
    }

    @Override
    public void enableRefreshTimer(int seconds) {
        DomGlobal.clearInterval(refreshTimerID);
        refreshTimerID = DomGlobal.setInterval(e -> {
            if (presenter.isDrawn()) {
                presenter.redraw();
            }
        }, seconds * 1000);
    }

    @Override
    public void cancelRefreshTimer() {
        DomGlobal.clearInterval(refreshTimerID);
    }

    public void displayMessage(String msg) {
        clear();
        rootPanel.appendChild(label.getElement());
        label.setText(msg);
    }

    @Override
    public HTMLElement getElement() {
        return rootPanel;
    }

}
