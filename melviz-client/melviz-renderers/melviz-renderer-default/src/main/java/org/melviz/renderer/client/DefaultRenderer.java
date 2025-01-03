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
package org.melviz.renderer.client;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.jboss.errai.ioc.client.container.SyncBeanManager;
import org.melviz.displayer.DisplayerSettings;
import org.melviz.displayer.DisplayerSubType;
import org.melviz.displayer.DisplayerType;
import org.melviz.displayer.client.AbstractRendererLibrary;
import org.melviz.displayer.client.Displayer;
import org.melviz.renderer.client.external.ExternalComponentDisplayer;
import org.melviz.renderer.client.metric.MetricDisplayer;
import org.melviz.renderer.client.selector.SelectorDisplayer;
import org.melviz.renderer.client.selector.SelectorDropDownDisplayer;
import org.melviz.renderer.client.selector.SelectorLabelSetDisplayer;
import org.melviz.renderer.client.selector.SelectorSliderDisplayer;
import org.melviz.renderer.client.table.TableDisplayer;

import static org.melviz.displayer.DisplayerSubType.METRIC_CARD;
import static org.melviz.displayer.DisplayerSubType.METRIC_CARD2;
import static org.melviz.displayer.DisplayerSubType.METRIC_PLAIN_TEXT;
import static org.melviz.displayer.DisplayerSubType.METRIC_QUOTA;
import static org.melviz.displayer.DisplayerSubType.SELECTOR_DROPDOWN;
import static org.melviz.displayer.DisplayerSubType.SELECTOR_LABELS;
import static org.melviz.displayer.DisplayerSubType.SELECTOR_SLIDER;
import static org.melviz.displayer.DisplayerType.EXTERNAL_COMPONENT;
import static org.melviz.displayer.DisplayerType.METRIC;
import static org.melviz.displayer.DisplayerType.SELECTOR;
import static org.melviz.displayer.DisplayerType.TABLE;

/**
 * Default renderer
 */
@ApplicationScoped
public class DefaultRenderer extends AbstractRendererLibrary {

    public static final String UUID = "default";

    @Inject
    protected SyncBeanManager beanManager;

    @PostConstruct
    private void init() {
        publishJsFunctions();
    }

    @Override
    public String getUUID() {
        return UUID;
    }

    @Override
    public String getName() {
        return "GWT Core";
    }

    @Override
    public boolean isDefault(DisplayerType type) {
        return TABLE.equals(type) ||
                SELECTOR.equals(type) ||
                METRIC.equals(type) ||
                EXTERNAL_COMPONENT.equals(type);
    }

    @Override
    public List<DisplayerType> getSupportedTypes() {
        return Arrays.asList(
                TABLE,
                SELECTOR,
                METRIC,
                EXTERNAL_COMPONENT);
    }

    @Override
    public List<DisplayerSubType> getSupportedSubtypes(DisplayerType displayerType) {
        switch (displayerType) {
            case METRIC:
                return Arrays.asList(METRIC_CARD, METRIC_CARD2, METRIC_QUOTA, METRIC_PLAIN_TEXT);
            case SELECTOR:
                return Arrays.asList(SELECTOR_DROPDOWN, SELECTOR_LABELS, SELECTOR_SLIDER);
            default:
                return Collections.emptyList();
        }
    }

    @Override
    public Displayer lookupDisplayer(DisplayerSettings displayerSettings) {
        DisplayerType type = displayerSettings.getType();
        DisplayerSubType subtype = displayerSettings.getSubtype();

        if (TABLE.equals(type)) {
            return  buildAndManageInstance(TableDisplayer.class);
        }
        if (SELECTOR.equals(type)) {
            if (SELECTOR_DROPDOWN.equals(subtype)) {
                return buildAndManageInstance(SelectorDropDownDisplayer.class);
            }
            if (SELECTOR_LABELS.equals(subtype)) {
                return buildAndManageInstance(SelectorLabelSetDisplayer.class);
            }
            if (SELECTOR_SLIDER.equals(subtype)) {
                return buildAndManageInstance(SelectorSliderDisplayer.class);
            }
            // Keep backward compatibility with 0.6 and prior versions
            return beanManager.lookupBean(SelectorDisplayer.class).newInstance();
        }
        if (METRIC.equals(type)) {
            MetricDisplayer displayer = beanManager.lookupBean(MetricDisplayer.class).newInstance();
            _metricDisplayerMap.put(displayer.getView().getUniqueId(), displayer);
            return displayer;
        }
        
        if (EXTERNAL_COMPONENT.equals(type)) {
            return beanManager.lookupBean(ExternalComponentDisplayer.class).newInstance();
        }

        return null;
    }

    private native void publishJsFunctions() /*-{
        $wnd.metricDisplayerDoFilter = $entry(@org.melviz.renderer.client.DefaultRenderer::metricDisplayerDoFilter(Ljava/lang/String;));
    }-*/;

    protected static Map<String,MetricDisplayer> _metricDisplayerMap = new HashMap<>();

    public static void metricDisplayerDoFilter(String displayerId) {
        MetricDisplayer displayer = _metricDisplayerMap.get(displayerId);
        if (displayer != null) {
            displayer.updateFilter();
        }
    }

    public static void closeAllDisplayers() {
        _metricDisplayerMap.clear();
    }
}
