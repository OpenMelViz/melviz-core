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
package org.melviz.dataset.client.engine;

import java.util.Date;

import com.google.gwt.i18n.client.DateTimeFormat;

public class ClientDateFormatterImpl implements ClientDateFormatter {

    @Override
    public String format(Date d, String pattern) {
        DateTimeFormat format  = DateTimeFormat.getFormat(pattern);
        return format.format(d);
    }
}
