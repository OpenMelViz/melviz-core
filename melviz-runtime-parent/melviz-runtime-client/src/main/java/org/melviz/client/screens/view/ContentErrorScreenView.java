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

import elemental2.dom.HTMLDivElement;
import elemental2.dom.HTMLElement;
import elemental2.dom.HTMLPreElement;
import org.jboss.errai.ui.shared.api.annotations.DataField;
import org.jboss.errai.ui.shared.api.annotations.Templated;
import org.melviz.client.resources.i18n.AppConstants;
import org.melviz.client.screens.ContentErrorScreen;

@Templated
@Dependent
public class ContentErrorScreenView implements ContentErrorScreen.View {

    @Inject
    @DataField
    HTMLDivElement errorContentRoot;

    @Inject
    @DataField
    HTMLDivElement errorContentScreenTitle;

    @Inject
    @DataField
    HTMLPreElement errorContentText;

    @Override
    public HTMLElement getElement() {
        return errorContentRoot;
    }

    @Override
    public void init(ContentErrorScreen presenter) {
        errorContentScreenTitle.innerHTML = AppConstants.errorContentTitle();
    }

    @Override
    public void showContentError(String errorContent) {
        errorContentText.innerHTML = errorContent;
    }

}
