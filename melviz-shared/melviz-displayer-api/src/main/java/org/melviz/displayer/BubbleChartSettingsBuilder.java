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
package org.melviz.displayer;

/**
 * A displayer settings builder for bubble charts.
 *
 * <pre>
 *   DisplayerSettingsFactory.newBubbleChartSettings()
 *   .title("Opportunities distribution by Country ")
 *   .width(500).height(350)
 *   .margins(20, 50, 50, 0)
 *   .column(COUNTRY, "Country")
 *   .column("opps", "Number of opportunities")
 *   .column(PROBABILITY, "Average probability")
 *   .column(COUNTRY, "Country")
 *   .column(EXPECTED_AMOUNT, "Expected amount")
 *   .buildSettings());
 * </pre>
 */
public interface BubbleChartSettingsBuilder<T extends BubbleChartSettingsBuilder> extends XAxisChartSettingsBuilder<T> {

}
