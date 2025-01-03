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
package org.melviz.displayer.impl;

import org.melviz.displayer.DisplayerSettings;
import org.melviz.displayer.DisplayerSubType;
import org.melviz.displayer.DisplayerType;
import org.melviz.displayer.PieChartSettingsBuilder;

public class PieChartSettingsBuilderImpl extends AbstractXAxisChartSettingsBuilder<PieChartSettingsBuilderImpl>
                                         implements PieChartSettingsBuilder<PieChartSettingsBuilderImpl> {

    protected DisplayerSettings createDisplayerSettings() {
        return new DisplayerSettings(DisplayerType.PIECHART, DisplayerSubType.PIE);
    }

    public PieChartSettingsBuilderImpl set3d(boolean b) {
        displayerSettings.setChart3D(b);
        return this;
    }

    @Override
    public PieChartSettingsBuilderImpl subType_Pie() {
        displayerSettings.setSubtype(DisplayerSubType.PIE);
        return this;
    }

    @Override
    public PieChartSettingsBuilderImpl subType_Pie_3d() {
        displayerSettings.setSubtype(DisplayerSubType.PIE_3D);
        return this;
    }

    @Override
    public PieChartSettingsBuilderImpl subType_Donut() {
        displayerSettings.setSubtype(DisplayerSubType.DONUT);
        return this;
    }

    @Override
    public PieChartSettingsBuilderImpl subType_Donut(String holeLabel) {
        displayerSettings.setSubtype(DisplayerSubType.DONUT);
        displayerSettings.setDonutHoleTitle(holeLabel);
        return this;
    }
}
