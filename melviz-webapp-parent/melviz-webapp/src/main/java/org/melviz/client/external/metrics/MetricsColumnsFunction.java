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
package org.melviz.client.external.metrics;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

import org.melviz.dataset.ColumnType;
import org.melviz.dataset.def.DataColumnDef;

/**
 * Default Micrometer columns definition
 *
 */
public class MetricsColumnsFunction implements Function<String, List<DataColumnDef>> {

    @Override
    public List<DataColumnDef> apply(String t) {
        return Arrays.asList(
                new DataColumnDef("metric", ColumnType.LABEL),
                new DataColumnDef("labels", ColumnType.LABEL),
                new DataColumnDef("value", ColumnType.NUMBER));
    }

}
