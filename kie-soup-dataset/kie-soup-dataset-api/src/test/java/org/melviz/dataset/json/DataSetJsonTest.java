/*
 
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
*/
package org.melviz.dataset.json;

import org.junit.Test;
import org.melviz.dataset.DataColumn;
import org.melviz.dataset.DataSet;
import org.melviz.dataset.DataSetFactory;
import org.melviz.json.JsonObject;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.melviz.dataset.date.Month.APRIL;
import static org.melviz.dataset.date.Month.AUGUST;
import static org.melviz.dataset.date.Month.DECEMBER;
import static org.melviz.dataset.date.Month.FEBRUARY;
import static org.melviz.dataset.date.Month.JANUARY;
import static org.melviz.dataset.date.Month.JULY;
import static org.melviz.dataset.date.Month.JUNE;
import static org.melviz.dataset.date.Month.MARCH;
import static org.melviz.dataset.date.Month.MAY;
import static org.melviz.dataset.date.Month.NOVEMBER;
import static org.melviz.dataset.date.Month.OCTOBER;
import static org.melviz.dataset.date.Month.SEPTEMBER;

public class DataSetJsonTest {

    DataSetJSONMarshaller datasetJsonMarshaller = DataSetJSONMarshaller.get();
    
    
    public static final String CONFLICTING_ID_DS_JSON = "{\n" + 
            "  \"column.0\": {\n" + 
            "    \"id\": \"ID\",\n" + 
            "    \"type\": \"NUMBER\",\n" + 
            "    \"values\": [\n" + 
            "      \"0.0\"\n" + 
            "    ]\n" + 
            "  },\n" + 
            "  \"column.1\": {\n" + 
            "    \"id\": \"ID\",\n" + 
            "    \"type\": \"NUMBER\",\n" + 
            "    \"values\": [\n" + 
            "      \"265.0\"\n" + 
            "    ]\n" + 
            "  }\n" + 
            "}";

    @Test
    public void testDataSetMarshalling() {
        DataSet original = DataSetFactory.newDataSetBuilder()
                .label("month")
                .number("2012")
                .number("2013")
                .number("2014")
                .row(JANUARY, 1000d, 2000d, 3000d)
                .row(FEBRUARY, 1400d, 2300d, 2000d)
                .row(MARCH, 1300d, 2000d, 1400d)
                .row(APRIL, 900d, 2100d, 1500d)
                .row(MAY, 1300d, 2300d, 1600d)
                .row(JUNE, 1010d, 2000d, 1500d)
                .row(JULY, 1050d, 2400d, 3000d)
                .row(AUGUST, 2300d, 2000d, 3200d)
                .row(SEPTEMBER, 1900d, 2700d, 3000d)
                .row(OCTOBER, 1200d, 2200d, 3100d)
                .row(NOVEMBER, 1400d, 2100d, 3100d)
                .row(DECEMBER, 1100d, 2100d, 4200d)
                .buildDataSet();

        JsonObject _jsonObj = datasetJsonMarshaller.toJson(original);
        assertNotNull(_jsonObj.toString());

        DataSet unmarshalled = datasetJsonMarshaller.fromJson(_jsonObj);
        assertEquals(unmarshalled, original);
    }
    
    @Test
    public void testDoubleNumberColumnJsonUnmarshall() {
        DataSet dataset = datasetJsonMarshaller.fromJson(CONFLICTING_ID_DS_JSON);
        
        assertEquals(2, dataset.getColumns().size());
        DataColumn cl0 = dataset.getColumns().get(0);
        assertEquals(0.0, cl0.getValues().get(0));
        assertEquals("ID", cl0.getId());
        
        DataColumn cl1 = dataset.getColumns().get(1);
        assertEquals(265.0, cl1.getValues().get(0));
        assertEquals("ID", cl1.getId());
        
        
    }
}
