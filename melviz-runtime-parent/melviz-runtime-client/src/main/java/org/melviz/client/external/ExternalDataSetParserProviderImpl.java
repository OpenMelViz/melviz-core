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
package org.melviz.client.external;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;

import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.i18n.client.DateTimeFormat.PredefinedFormat;
import org.melviz.dataset.client.ExternalDataSetParserProvider;
import org.melviz.dataset.json.ExternalDataSetJSONParser;

@ApplicationScoped
public class ExternalDataSetParserProviderImpl implements ExternalDataSetParserProvider {

    ExternalDataSetJSONParser parser;

    @PostConstruct
    public void init() {
        var format = DateTimeFormat.getFormat(PredefinedFormat.ISO_8601);
        parser = new ExternalDataSetJSONParser(format::parse);
    }

    public ExternalDataSetJSONParser get() {
        return parser;

    }

}
