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
package org.melviz.displayer.client.widgets.filter;

import java.util.Date;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;

import elemental2.dom.HTMLElement;
import org.uberfire.client.mvp.UberElemental;
import org.uberfire.mvp.Command;

@Dependent
public class DateParameterEditor implements FunctionParameterEditor {

    public interface View extends UberElemental<DateParameterEditor> {

        Date getValue();

        void setValue(Date value);

        void setWidth(int width);
    }

    Command onChangeCommand = () -> {
    };
    Command onFocusCommand = () -> {
    };
    Command onBlurCommand = () -> {
    };
    View view;
    Date currentValue = null;

    @Inject
    public DateParameterEditor(View view) {
        this.view = view;
        this.view.init(this);
    }

    public void setOnChangeCommand(Command onChangeCommand) {
        this.onChangeCommand = onChangeCommand;
    }

    public void setOnFocusCommand(Command onFocusCommand) {
        this.onFocusCommand = onFocusCommand;
    }

    public void setOnBlurCommand(Command onBlurCommand) {
        this.onBlurCommand = onBlurCommand;
    }

    public Date getValue() {
        return view.getValue();
    }

    public void setValue(Date value) {
        Command backup = onChangeCommand;
        this.currentValue = value;
        try {
            onChangeCommand = null;
            view.setValue(value);
        } finally {
            onChangeCommand = backup;
        }
    }

    public void setWidth(int width) {
        if (width > 0) {
            view.setWidth(width);
        }
    }

    @Override
    public void setFocus(boolean focus) {}

    void onChange() {
        if (onChangeCommand != null && !currentValue.equals(getValue())) {
            currentValue = getValue();
            onChangeCommand.execute();
        }
    }

    void onBlur() {
        if (onBlurCommand != null) {
            onBlurCommand.execute();
        }
    }

    void onFocus() {
        if (onFocusCommand != null) {
            onFocusCommand.execute();
        }
    }
    
    public HTMLElement getElement() {
        return view.getElement();
    }
}
