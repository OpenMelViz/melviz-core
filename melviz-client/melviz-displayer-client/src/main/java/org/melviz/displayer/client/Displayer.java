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
package org.melviz.displayer.client;

import elemental2.dom.HTMLElement;
import org.melviz.displayer.DisplayerConstraints;
import org.melviz.displayer.DisplayerSettings;
import org.melviz.displayer.client.formatter.ValueFormatter;

/**
 * A Displayer takes care of drawing a DisplayerSettings instance.
 */
public interface Displayer extends DisplayerListener {

    /**
     * The data displayer to draw.
     */
    void setDisplayerSettings(DisplayerSettings displayerSettings);

    DisplayerSettings getDisplayerSettings();

    /**
     * The handler used to fetch and manipulate the data set.
     */
    void setDataSetHandler(DataSetHandler dataSetHandler);

    DataSetHandler getDataSetHandler();

    /**
     * Every Displayer implementation must define the set of features it supports as well as other behavioral settings.
     * These "constrains" are needed for two main reasons:
     * <ul>
     *     <li>Validation purposes: in order to ensure the DisplayerSettings are valid and ready for rendering.</li>
     *     <li>Edition purposes: in order to let the DisplayerEditor know what features/settings/behaviour this Displayer implementation supports.</li>
     * </ul>
     */
    DisplayerConstraints getDisplayerConstraints();

    /**
     * Add a listener interested in receive events generated within this displayer component.
     */
    void addListener(DisplayerListener... listeners);

    /**
     * Registers a custom formatter for the given column
     */
    void addFormatter(String columnId, ValueFormatter formatter);

    /**
     * Draw the chart
     */
    void draw();

    /**
     * Same as draw but does not necessary implies to repaint everything again.
     * It's just a matter of update & display the latest data set changes.
     */
    void redraw();

    /**
     * Check if the displayer is completely drawn.
     */
    boolean isDrawn();

    /**
     * Enables or disables the automatic refresh capability (enabled by default).
     * @see DisplayerSettings#getRefreshInterval()
     */
    void setRefreshOn(boolean enabled);

    /**
     * Check if the automatic refresh is on.
     */
    boolean isRefreshOn();

    /**
     * Frees any resource the displayer is consuming.
     */
    void close();

    HTMLElement getElement();
}
