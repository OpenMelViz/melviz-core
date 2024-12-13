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
package org.melviz.dataset;

import org.melviz.dataset.impl.DataSetBuilderImpl;
import org.melviz.dataset.impl.DataSetImpl;

/**
 * Factory class for building DataSet instances.
 */
public final class DataSetFactory {

    private DataSetFactory() {
        // no op
    }

    public static DataSet newEmptyDataSet() {
        return new DataSetImpl();
    }

    public static DataSetBuilder newDataSetBuilder() {
        return new DataSetBuilderImpl();
    }

}
