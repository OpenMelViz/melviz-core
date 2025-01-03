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
package org.melviz.displayer.client;

import elemental2.dom.HTMLElement;
import org.melviz.common.client.error.ClientRuntimeError;
import org.melviz.displayer.DisplayerConstraints;

public class DisplayerMock extends AbstractDisplayer<AbstractDisplayer.View> {

    private AbstractDisplayer.View view;
    private DisplayerConstraints constraints;
    private boolean ignoreError;

    public DisplayerMock(AbstractDisplayer.View view, DisplayerConstraints constraints) {
        this.view = view;
        this.constraints = constraints;
    }
    
    public DisplayerMock(AbstractDisplayer.View view, DisplayerConstraints constraints, boolean ignoreError) {
        this.view = view;
        this.constraints = constraints;
        this.ignoreError = ignoreError;
    }

    @Override
    public AbstractDisplayer.View getView() {
        return view;
    }

    @Override
    public DisplayerConstraints createDisplayerConstraints() {
        return constraints;
    }

    @Override
    protected void createVisualization() {
        // Mock
    }

    @Override
    protected void updateVisualization() {
        // Mock
    }

    @Override
    public void handleError(ClientRuntimeError error) {
        super.handleError(error);
        if (! ignoreError) {
            throw new RuntimeException(error.getRootCause());
        }
    }

    @Override
    public HTMLElement getElement() {
        return null;
    }
}
