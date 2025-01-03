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
import org.jboss.errai.ui.shared.api.annotations.DataField;
import org.jboss.errai.ui.shared.api.annotations.Templated;
import org.melviz.client.screens.DashboardsListScreen;
import org.melviz.client.widgets.DashboardCard;
import org.melviz.client.widgets.UploadWidget;

@Templated
@Dependent
public class DashboardsListScreenView implements DashboardsListScreen.View {

    @Inject
    @DataField
    HTMLDivElement dashboardsListRoot;

    @Inject
    @DataField
    HTMLDivElement uploadWidgetContainer;

    @Inject
    @DataField
    HTMLDivElement dashboardsList;

    @Inject
    UploadWidget uploadWidget;

    @Override
    public HTMLElement getElement() {
        return dashboardsListRoot;
    }

    @Override
    public void init(DashboardsListScreen presenter) {
        uploadWidgetContainer.appendChild(uploadWidget.getElement());
    }

    @Override
    public void addCard(DashboardCard card) {
        dashboardsList.appendChild(card.getElement());
    }

    @Override
    public void clear() {
        dashboardsList.innerHTML = "";
    }

    @Override
    public void disableUpload() {
        uploadWidgetContainer.remove();
    }

}