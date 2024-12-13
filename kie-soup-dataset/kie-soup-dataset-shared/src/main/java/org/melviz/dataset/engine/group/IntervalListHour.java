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
package org.melviz.dataset.engine.group;

import java.util.Date;

import org.melviz.dataset.group.ColumnGroup;
import org.melviz.dataset.group.Interval;

/**
 * List of the 24-hor intervals present in a day.
 */
public class IntervalListHour extends IntervalListSecond {

    public IntervalListHour(ColumnGroup columnGroup) {
        super(columnGroup, 24);
    }

    public Interval locateInterval(Object value) {
        Date d = (Date) value;
        int sec = d.getHours();
        return intervalMap.get(sec);
    }
}
