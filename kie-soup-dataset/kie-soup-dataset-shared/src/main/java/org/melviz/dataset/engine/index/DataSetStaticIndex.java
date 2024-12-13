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
package org.melviz.dataset.engine.index;

import org.melviz.dataset.DataSet;
import org.melviz.dataset.engine.index.stats.DataSetIndexStats;
import org.melviz.dataset.engine.index.stats.DataSetIndexStatsImpl;

/**
 * An index for static data sets.
 */
public class DataSetStaticIndex extends DataSetIndex {

    private DataSet dataSet;

    public DataSetStaticIndex(DataSet dataSet) {
        super();
        this.dataSet = dataSet;
    }

    public DataSet getDataSet() {
        return dataSet;
    }

    public DataSetIndexStats getStats() {
        return new DataSetIndexStatsImpl(this);
    }
}

