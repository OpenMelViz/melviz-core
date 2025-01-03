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
package org.melviz.displayer.client.widgets;

import javax.annotation.PostConstruct;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;

import elemental2.dom.DomGlobal;
import elemental2.dom.HTMLDivElement;
import elemental2.dom.HTMLElement;
import org.jboss.errai.ui.shared.api.annotations.DataField;
import org.jboss.errai.ui.shared.api.annotations.Templated;
import org.melviz.patternfly.code.CodeView;
import org.melviz.patternfly.panel.Panel;

@Dependent
@Templated
public class DisplayerErrorWidget {

    @Inject
    @DataField
    private HTMLDivElement displayerErrorRoot;

    @Inject
    @DataField
    private HTMLDivElement errorBody;

    @Inject
    @DataField
    private HTMLDivElement errorDetailsContainer;

    @Inject
    Panel panel;

    @Inject
    CodeView code;

    @PostConstruct
    public void init() {
        errorDetailsContainer.appendChild(panel.getElement());
        panel.setContent(code.getElement());
        panel.setTitle("Details");
        panel.setCollapsed(true);
    }

    public void show(String message, Throwable t) {
        errorBody.textContent = message;
        if (t != null) {
            var errorDetails = buildErrorDetails(t);
            code.setContent(errorDetails);
            DomGlobal.console.debug(t);
        }
    }

    private String buildErrorDetails(Throwable t) {
        var sb = new StringBuilder();
        var cause = t.getCause();
        sb.append(t.getMessage());
        while (cause != null) {
            sb.append("\n  Caused by: " + cause.getMessage());
            cause = cause.getCause();
        }
        return sb.toString();
    }

    public HTMLElement getElement() {
        return displayerErrorRoot;
    }

}
