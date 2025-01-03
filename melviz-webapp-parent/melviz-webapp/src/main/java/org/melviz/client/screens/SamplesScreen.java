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

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import elemental2.dom.HTMLElement;
import org.jboss.errai.ioc.client.api.ManagedInstance;
import org.melviz.client.place.Place;
import org.melviz.client.services.SamplesService;
import org.melviz.client.widgets.SampleCard;
import org.melviz.client.widgets.SamplesCardRow;
import org.uberfire.client.mvp.UberElemental;

/**
 * Screen displayed when there's no dashboards available and samples url is configured
 *
 */
@ApplicationScoped
public class SamplesScreen implements Place {

    public static final String ID = "SamplesScreen";

    @Inject
    View view;

    @Inject
    Router router;

    @Inject
    SamplesService samplesService;

    @Inject
    ManagedInstance<SampleCard> sampleCardInstance;

    @Inject
    ManagedInstance<SamplesCardRow> samplesCardRowInstance;

    public interface View extends UberElemental<SamplesScreen> {

        void addRows(List<SamplesCardRow> rows);

        void clear();
    }

    @PostConstruct
    public void init() {
        view.init(this);
    }

    @Override
    public void onOpen() {
        var rows = new ArrayList<SamplesCardRow>();
        samplesService.samplesByCategory().forEach((cat, samples) -> {
            var samplesCards = new ArrayList<SampleCard>();
            samples.forEach(sample -> {
                var card = sampleCardInstance.get();
                card.setSample(sample, () -> router.loadDashboard(sample.getSourceUrl()));
                samplesCards.add(card);
            });
            var row = samplesCardRowInstance.get();
            row.setCategoryAndSamples(cat, samplesCards);
            rows.add(row);
        });
        view.addRows(rows);
    }

    @PreDestroy
    public void clear() {
        sampleCardInstance.destroyAll();
        samplesCardRowInstance.destroyAll();
        view.clear();
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
