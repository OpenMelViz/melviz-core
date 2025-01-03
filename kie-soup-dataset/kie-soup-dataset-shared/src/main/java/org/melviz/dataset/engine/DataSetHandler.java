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
package org.melviz.dataset.engine;

import org.melviz.dataset.filter.DataSetFilter;
import org.melviz.dataset.group.DataSetGroup;
import org.melviz.dataset.sort.DataSetSort;

/**
 * Interface addressed to execute single operations over a data set.
 */
public interface DataSetHandler extends DataSetRowSet {

    DataSetHandler group(DataSetGroup op);
    DataSetHandler filter(DataSetFilter op);
    DataSetHandler sort(DataSetSort op);

}