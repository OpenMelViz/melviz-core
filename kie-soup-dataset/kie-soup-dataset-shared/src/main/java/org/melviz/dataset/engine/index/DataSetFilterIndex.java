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

import java.util.List;

import org.melviz.dataset.filter.ColumnFilter;

/**
 * An index for filtered data sets.
 */
public class DataSetFilterIndex extends DataSetIndexNode {

    private ColumnFilter columnFilter;

    public DataSetFilterIndex(ColumnFilter columnFilter, List<Integer> rows) {
        super(null, rows, 0);
        this.columnFilter = columnFilter;
    }

    public ColumnFilter getColumnFilter() {
        return columnFilter;
    }

    public String toString() {
        StringBuilder out = new StringBuilder(super.toString());
        if (columnFilter != null) out.append(columnFilter.toString());
        return out.toString();
    }
}

