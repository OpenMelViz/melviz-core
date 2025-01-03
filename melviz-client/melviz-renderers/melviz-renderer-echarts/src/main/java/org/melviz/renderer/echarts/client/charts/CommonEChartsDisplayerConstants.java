/*
 
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.melviz.renderer.echarts.client.charts;

import org.melviz.dataset.DataSetLookupConstraints;
import org.melviz.displayer.DisplayerAttributeDef;
import org.melviz.displayer.DisplayerAttributeGroupDef;
import org.melviz.displayer.DisplayerConstraints;

/**
 * A common class for displayer constraints supported by a ECharts displayer. 
 */
public class CommonEChartsDisplayerConstants {
    
    private DataSetLookupConstraints lookupConstraints;

    public CommonEChartsDisplayerConstants(DataSetLookupConstraints lookupConstraints) {
        this.lookupConstraints = lookupConstraints;
    }
    
    public DisplayerConstraints create() {
        return new DisplayerConstraints(lookupConstraints).supportsAttribute(DisplayerAttributeDef.TYPE)
                                                          .supportsAttribute(DisplayerAttributeDef.RENDERER)
                                                          .supportsAttribute(DisplayerAttributeGroupDef.COLUMNS_GROUP)
                                                          .supportsAttribute(DisplayerAttributeGroupDef.FILTER_GROUP)
                                                          .supportsAttribute(DisplayerAttributeGroupDef.REFRESH_GROUP)
                                                          .supportsAttribute(DisplayerAttributeGroupDef.GENERAL_GROUP)
                                                          .supportsAttribute(DisplayerAttributeGroupDef.CHART_RESIZABLE)
                                                          .supportsAttribute(DisplayerAttributeGroupDef.CHART_WIDTH)
                                                          .supportsAttribute(DisplayerAttributeGroupDef.CHART_HEIGHT)
                                                          .supportsAttribute(DisplayerAttributeDef.CHART_BGCOLOR)
                                                          .supportsAttribute(DisplayerAttributeGroupDef.CHART_MARGIN_GROUP)
                                                          .supportsAttribute(DisplayerAttributeGroupDef.CHART_LEGEND_GROUP);
                                                            
    }

}