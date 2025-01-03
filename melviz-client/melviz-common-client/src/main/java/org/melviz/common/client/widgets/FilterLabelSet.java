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
package org.melviz.common.client.widgets;

import javax.inject.Inject;

import elemental2.dom.HTMLElement;
import org.jboss.errai.ioc.client.container.SyncBeanManager;
import org.uberfire.client.mvp.UberElemental;

public class FilterLabelSet {

    public interface View extends UberElemental<FilterLabelSet> {

        void clearAll();

        void setClearAllEnabled(boolean enabled);

        void addLabel(FilterLabel label);
    }

    private View view;
    private SyncBeanManager beanManager;
    private Runnable onClearAllCommand;
    private int numberOfLabels = 0;

    @Inject
    public FilterLabelSet(View view, SyncBeanManager beanManager) {
        this.view = view;
        this.beanManager = beanManager;
        this.view.init(this);
        this.view.setClearAllEnabled(false);
    }

    public HTMLElement getElement() {
        return view.getElement();
    }

    public void clear() {
        view.clearAll();
        view.setClearAllEnabled(false);
        numberOfLabels = 0;
    }

    public FilterLabel addLabel(String label) {
        FilterLabel filterLabel = beanManager.lookupBean(FilterLabel.class).newInstance();
        filterLabel.setLabel(label);
        view.addLabel(filterLabel);
        numberOfLabels++;
        view.setClearAllEnabled(numberOfLabels > 1);
        return filterLabel;
    }

    public void setOnClearAllCommand(Runnable onClearAllCommand) {
        this.onClearAllCommand = onClearAllCommand;
    }

    void onClearAll() {
        this.clear();
        if (onClearAllCommand != null) {
            onClearAllCommand.run();
        }
    }
}
