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
package org.melviz.dataset.engine.function;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.melviz.dataset.group.AggregateFunctionType;

/**
 * It calculates the number of distinct occurrences inside a given collection.
 */
public class DistinctFunction extends AbstractFunction {

    public DistinctFunction() {
        super();
    }

    public AggregateFunctionType getType() {
        return AggregateFunctionType.DISTINCT;
    }

    public Object aggregate(List values) {
        if (values == null || values.isEmpty()) {
            return 0d;
        }

        // Return the number of distinct items in the collection.
        Set distincts = new HashSet();
        Iterator it = values.iterator();
        while (it.hasNext()) {
            Object o = it.next();
            if (distincts.contains(o)) {
                continue;
            }
            distincts.add(o);
        }
        return (double) distincts.size();
    }

    public Object aggregate(List values, List<Integer> rows) {
        if (rows == null) {
            return aggregate(values);
        }
        if (rows.isEmpty()) {
            return 0d;
        }
        if (values == null || values.isEmpty()) {
            return 0d;
        }

        // Return the number of distinct items in the collection.
        Set distincts = new HashSet();
        for (Integer row : rows) {
            Object o = values.get(row);
            if (distincts.contains(o)) {
                continue;
            }
            distincts.add(o);
        }
        return (double) distincts.size();
    }
}
