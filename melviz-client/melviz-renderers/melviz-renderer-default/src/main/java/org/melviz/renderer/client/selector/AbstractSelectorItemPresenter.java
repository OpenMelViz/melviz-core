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
package org.melviz.renderer.client.selector;

import org.uberfire.mvp.Command;

public abstract class AbstractSelectorItemPresenter implements SelectorItemPresenter {

    protected Command onSelectCommand;
    protected Command onResetCommand;
    protected int id;
    protected boolean selected = false;

    public void init(int id, String value, String descr) {
        this.id = id;
        getView().setValue(value);
        getView().setDescription(descr);
        this.reset();
    }

    public int getId() {
        return id;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setOnSelectCommand(Command onSelectCommand) {
        this.onSelectCommand = onSelectCommand;
    }

    public void setOnResetCommand(Command onResetCommand) {
        this.onResetCommand = onResetCommand;
    }

    public void select() {
        selected = true;
        getView().select();
    }

    public void reset() {
        selected = false;
        getView().reset();
    }

    // View notifications

    public void onItemClick() {
        if (selected) {
            selected = false;
            getView().reset();

            if (onResetCommand != null) {
                onResetCommand.execute();
            }
        }
        else {
            selected = true;
            getView().select();

            if (onSelectCommand != null) {
                onSelectCommand.execute();
            }
        }
    }
}
