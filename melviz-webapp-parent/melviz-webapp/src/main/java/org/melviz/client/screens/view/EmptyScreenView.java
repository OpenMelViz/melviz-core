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
package org.melviz.client.screens.view;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;

import com.google.gwt.dom.client.Style.Display;
import elemental2.dom.HTMLButtonElement;
import elemental2.dom.HTMLDivElement;
import elemental2.dom.HTMLElement;
import elemental2.dom.HTMLParagraphElement;
import org.jboss.errai.ui.shared.api.annotations.DataField;
import org.jboss.errai.ui.shared.api.annotations.Templated;
import org.melviz.client.resources.i18n.AppConstants;
import org.melviz.client.screens.EmptyScreen;
import org.melviz.client.widgets.UploadWidget;

@Templated
@Dependent
public class EmptyScreenView implements EmptyScreen.View {

    @Inject
    @DataField
    HTMLDivElement emptyImport;

    @Inject
    @DataField
    HTMLDivElement uploadContainer;

    @Inject
    @DataField
    HTMLDivElement trySamplesContainer;

    @Inject
    @DataField
    HTMLParagraphElement subTitleParagraph;

    @Inject
    @DataField
    HTMLButtonElement trySamples;

    @Inject
    UploadWidget uploadWidget;

    @Override
    public HTMLElement getElement() {
        return emptyImport;
    }

    @Override
    public void init(EmptyScreen presenter) {
        uploadContainer.appendChild(uploadWidget.getElement());
    }

    @Override
    public void editorMode() {
        uploadContainer.style.display = Display.NONE.getCssName();
        subTitleParagraph.textContent = AppConstants.emptyEditorMode();
    }

    @Override
    public void noModel() {
        subTitleParagraph.textContent = AppConstants.emptyClientMode();
    }

    @Override
    public void modelId(String modelId) {
        subTitleParagraph.innerHTML = AppConstants.emptyWithImportId(modelId);
    }

    @Override
    public void enableSamplesButton(Runnable action) {
        uploadContainer.style.display = Display.NONE.getCssName();
        subTitleParagraph.textContent = subTitleParagraph.textContent + " " + AppConstants
                .emptyScreenTrySamples();
        trySamplesContainer.style.display = Display.INLINE_BLOCK.getCssName();
        trySamples.onclick = e -> {
            action.run();
            return true;
        };
    }
}
