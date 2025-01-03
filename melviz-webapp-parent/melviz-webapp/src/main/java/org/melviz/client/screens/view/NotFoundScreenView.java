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
import elemental2.dom.HTMLParagraphElement;
import org.jboss.errai.ui.shared.api.annotations.DataField;
import org.jboss.errai.ui.shared.api.annotations.Templated;
import org.melviz.client.resources.i18n.AppConstants;
import org.melviz.client.screens.NotFoundScreen;

@Dependent
@Templated
public class NotFoundScreenView implements NotFoundScreen.View {
    
    @Inject
    @DataField
    HTMLDivElement root;
    
    @Inject
    @DataField
    HTMLParagraphElement subTitle;

    @Override
    public HTMLElement getElement() {
        return root;
    }

    @Override
    public void init(NotFoundScreen presenter) {
        // do nothing
    }
    
    @Override
    public void setNotFoundDashboard(String perspectiveName) {
        subTitle.textContent = AppConstants.notFoundDashboard(perspectiveName);
    }
    
}