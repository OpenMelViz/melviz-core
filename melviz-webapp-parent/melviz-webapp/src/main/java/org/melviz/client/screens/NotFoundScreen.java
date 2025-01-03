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

package org.melviz.client.screens;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import elemental2.dom.HTMLElement;
import org.melviz.client.RuntimeClientLoader;
import org.melviz.client.place.Place;
import org.uberfire.client.mvp.UberElemental;

/**
 * Screen displayed when a perspective is not found.
 *
 */
@ApplicationScoped
public class NotFoundScreen implements Place {

    public static final String ID = "NotFoundScreen";

    public interface View extends UberElemental<NotFoundScreen> {

        void setNotFoundDashboard(String perspectiveName);
    }

    @Inject
    View view;

    @Inject
    RuntimeClientLoader loader;

    @Override
    public void onOpen() {
        var dashboard = loader.getImportId();
        if (dashboard != null) {
            view.setNotFoundDashboard(dashboard);
        }
    }

    @Override
    public String getId() {
        return ID;
    }

    @Override
    public HTMLElement getElement() {
        return view.getElement();
    }

}
