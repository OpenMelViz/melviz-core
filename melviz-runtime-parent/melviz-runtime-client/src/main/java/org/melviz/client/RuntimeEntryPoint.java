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

package org.melviz.client;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import elemental2.dom.DomGlobal;
import elemental2.dom.Element;
import org.jboss.errai.common.client.dom.elemental2.Elemental2DomUtil;
import org.jboss.errai.ioc.client.api.EntryPoint;
import org.melviz.client.place.PlaceManager;
import org.melviz.client.screens.Router;
import org.melviz.patternfly.busyindicator.BusyIndicator;

@EntryPoint
@ApplicationScoped
public class RuntimeEntryPoint {

    @Inject
    Elemental2DomUtil domUtil;

    @Inject
    PlaceManager placeManager;

    @Inject
    Router router;

    @Inject
    BusyIndicator busyIndicator;

    @PostConstruct
    public void onLoad() {
        var root = DomGlobal.document.getElementById("app");
        root.appendChild(busyIndicator.getElement());
        DomGlobal.document.body.appendChild(root);
        hideLoading();
        placeManager.setup(root);
        router.doRoute();

    }

    private void hideLoading() {
        Element loading = DomGlobal.document.getElementById("loading");
        if (loading != null) {
            loading.remove();
        }
    }

}
