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

package org.melviz.client.widgets.view;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;

import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ClickEvent;
import elemental2.dom.HTMLButtonElement;
import elemental2.dom.HTMLDivElement;
import elemental2.dom.HTMLElement;
import elemental2.dom.HTMLFormElement;
import elemental2.dom.HTMLInputElement;
import org.jboss.errai.ui.shared.api.annotations.DataField;
import org.jboss.errai.ui.shared.api.annotations.EventHandler;
import org.jboss.errai.ui.shared.api.annotations.Templated;
import org.melviz.client.RuntimeCommunication;
import org.melviz.client.resources.i18n.AppConstants;
import org.melviz.client.widgets.UploadWidget;
import org.melviz.patternfly.busyindicator.BusyIndicator;

@Dependent
@Templated
public class UploadWidgetView implements UploadWidget.View {

    UploadWidget presenter;

    @Inject
    @DataField
    HTMLDivElement uploadButtonContainer;

    @Inject
    @DataField
    HTMLButtonElement btnImport;

    @Inject
    @DataField
    HTMLFormElement uploadForm;

    @Inject
    @DataField
    HTMLInputElement inputFile;

    @Inject
    @DataField
    HTMLInputElement inputFileName;

    @Inject
    RuntimeCommunication runtimeCommunication;

    @Inject
    BusyIndicator busyIndicator;

    @Override
    public void init(UploadWidget presenter) {
        this.presenter = presenter;
    }

    @Override
    public void loading() {
        busyIndicator.show(AppConstants.uploadingDashboards());
    }

    @Override
    public void stopLoading() {
        busyIndicator.hide();
    }

    @EventHandler("btnImport")
    public void handleImport(ClickEvent e) {
        inputFile.accept = presenter.getAcceptUpload();
        inputFile.click();
    }

    @EventHandler("inputFile")
    public void handleInputFileChange(ChangeEvent e) {
        var importName = presenter.retrieveFileName(inputFile.value);
        inputFileName.value = importName;
        presenter.submit(importName, inputFile.files.getAt(0), uploadForm);
    }

    @Override
    public HTMLElement getElement() {
        return uploadButtonContainer;
    }

    @Override
    public void importSuccess(String importName) {
        runtimeCommunication.showSuccess(AppConstants.importSuccess(importName));
    }

    @Override
    public void errorLoadingDashboard(String message) {
        runtimeCommunication.showWarning(AppConstants.notAbleToLoadDashboard(message));
    }

}
