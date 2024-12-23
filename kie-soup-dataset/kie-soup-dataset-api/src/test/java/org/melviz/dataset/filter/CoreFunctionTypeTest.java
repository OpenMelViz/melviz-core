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
package org.melviz.dataset.filter;

import org.junit.Test;
import org.melviz.dataset.ColumnType;

import static org.assertj.core.api.Assertions.assertThat;
import static org.melviz.dataset.filter.CoreFunctionType.IN;
import static org.melviz.dataset.filter.CoreFunctionType.LIKE_TO;
import static org.melviz.dataset.filter.CoreFunctionType.NOT_IN;
import static org.melviz.dataset.filter.CoreFunctionType.TIME_FRAME;

public class CoreFunctionTypeTest {

    @Test
    public void getSupportedTypeTest() {
        
        CoreFunctionType[] dateFunctionTypesUnsupported = {LIKE_TO, IN, NOT_IN};

        CoreFunctionType[] textLabelFunctionTypesUnsupported = {TIME_FRAME};

        CoreFunctionType[] numericFunctionTypesUnsupported = {LIKE_TO, TIME_FRAME};

        assertThat(CoreFunctionType.getSupportedTypes(ColumnType.DATE))
                .doesNotContain(dateFunctionTypesUnsupported);
        
        assertThat(CoreFunctionType.getSupportedTypes(ColumnType.LABEL))
                .doesNotContain(textLabelFunctionTypesUnsupported);

        assertThat(CoreFunctionType.getSupportedTypes(ColumnType.NUMBER))
                .doesNotContain(numericFunctionTypesUnsupported);

        assertThat(CoreFunctionType.getSupportedTypes(ColumnType.TEXT))
                .doesNotContain(textLabelFunctionTypesUnsupported);
    }
}
