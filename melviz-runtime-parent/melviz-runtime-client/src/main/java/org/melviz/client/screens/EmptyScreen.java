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

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import elemental2.dom.HTMLElement;
import org.melviz.client.RuntimeClientLoader;
import org.melviz.client.place.Place;
import org.uberfire.client.mvp.UberElemental;

/**
 * Screen displayed when there's no dashboards available.
 *
 */
@ApplicationScoped
public class EmptyScreen implements Place {

    public static final String ID = "EmptyScreen";

    @Inject
    View view;

    @Inject
    Router router;

    @Inject
    RuntimeClientLoader loader;

    public interface View extends UberElemental<EmptyScreen> {

        void editorMode();

        void noModel();

        void modelId(String modelId);

        void enableSamplesButton(Runnable action);

    }

    @PostConstruct
    public void init() {
        view.init(this);
    }

    @Override
    public void onOpen() {
        var modelId = loader.getImportId();
        if (loader.isEditor()) {
            view.editorMode();
        } else if (modelId != null && !modelId.trim().equals("")) {
            view.modelId(modelId);
        } else {
            view.noModel();
        }

        if (loader.hasSamples()) {
            view.enableSamplesButton(router::goToSamplesScreen);
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
