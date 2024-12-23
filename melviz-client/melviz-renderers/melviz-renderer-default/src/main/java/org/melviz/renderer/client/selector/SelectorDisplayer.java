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

import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;

import org.melviz.dataset.ColumnType;
import org.melviz.dataset.DataColumn;
import org.melviz.dataset.DataSetLookupConstraints;
import org.melviz.dataset.group.DataSetGroup;
import org.melviz.displayer.ColumnSettings;
import org.melviz.displayer.DisplayerAttributeDef;
import org.melviz.displayer.DisplayerAttributeGroupDef;
import org.melviz.displayer.DisplayerConstraints;
import org.melviz.displayer.client.AbstractDisplayer;
import org.melviz.displayer.client.Displayer;

@Dependent
public class SelectorDisplayer extends AbstractDisplayer<SelectorDisplayer.View> {

    public interface View extends AbstractDisplayer.View<SelectorDisplayer> {

        void showSelectHint(String column);

        void showResetHint(String column);

        void clearItems();

        void addItem(String id, String value, boolean selected);

        String getSelectedId();

        int getItemCount();

        void setItemTitle(int id, String title);

        void setFilterEnabled(boolean enabled);

        String getGroupsTitle();

        String getColumnsTitle();
    }

    View view;
    protected boolean filterOn = false;

    @Inject
    public SelectorDisplayer(View view) {
        this.view = view;
    }

    @PostConstruct
    public void init() {
        view.init(this);
    }

    @Override
    public View getView() {
        return view;
    }

    @Override
    public DisplayerConstraints createDisplayerConstraints() {

        DataSetLookupConstraints lookupConstraints = new DataSetLookupConstraints()
                .setGroupRequired(true)
                .setGroupColumn(true)
                .setMaxColumns(-1)
                .setMinColumns(1)
                .setExtraColumnsAllowed(true)
                .setGroupsTitle(view.getGroupsTitle())
                .setColumnsTitle(view.getColumnsTitle())
                .setColumnTypes(new ColumnType[]{
                                                 ColumnType.LABEL});

        return new DisplayerConstraints(lookupConstraints)
                .supportsAttribute(DisplayerAttributeDef.TYPE)
                .supportsAttribute(DisplayerAttributeGroupDef.COLUMNS_GROUP)
                .supportsAttribute(DisplayerAttributeGroupDef.FILTER_GROUP)
                .supportsAttribute(DisplayerAttributeGroupDef.REFRESH_GROUP)
                .supportsAttribute(DisplayerAttributeGroupDef.GENERAL_GROUP);
    }

    @Override
    protected void createVisualization() {
        view.setFilterEnabled(displayerSettings.isFilterEnabled());
        updateVisualization();
    }

    @Override
    protected void updateVisualization() {
        view.clearItems();
        DataColumn firstColumn = dataSet.getColumnByIndex(0);
        String firstColumnId = firstColumn.getId();
        ColumnSettings columnSettings = displayerSettings.getColumnSettings(firstColumn);
        String firstColumnName = columnSettings.getColumnName();
        List<Integer> currentFilter = super.filterIndexes(firstColumnId);

        // Add a selector hint according to the filter status
        if (currentFilter.isEmpty()) {
            view.showSelectHint(firstColumnName);
        } else {
            view.showResetHint(firstColumnName);
        }

        // Generate the list entries from the current data set
        for (int i = 0; i < dataSet.getRowCount(); i++) {

            Object obj = dataSet.getValueAt(i, 0);
            if (obj == null) {
                continue;
            }

            String value = super.formatValue(i, 0);
            boolean selected = currentFilter != null && currentFilter.contains(i);
            view.addItem(Integer.toString(i), value, selected);

            // Generate an option tooltip (only if extra data set columns are defined)
            int ncolumns = dataSet.getColumns().size();
            if (ncolumns > 1) {
                StringBuilder out = new StringBuilder();
                for (int j = 1; j < ncolumns; j++) {

                    DataColumn extraColumn = dataSet.getColumnByIndex(j);
                    columnSettings = displayerSettings.getColumnSettings(extraColumn);
                    String extraColumnName = columnSettings.getColumnName();
                    Object extraValue = dataSet.getValueAt(i, j);

                    if (extraValue != null) {
                        out.append(j > 1 ? "  " : "");
                        String formattedValue = super.formatValue(i, j);
                        out.append(extraColumnName).append("=").append(formattedValue);
                    }
                }
                view.setItemTitle(view.getItemCount() - 1, out.toString());
            }
        }
    }

    // View notifications

    public void onItemSelected() {

        // Reset the current filter (if any)
        DataColumn firstColumn = dataSet.getColumnByIndex(0);
        String firstColumnId = firstColumn.getId();
        List<Integer> currentFilter = filterIndexes(firstColumnId);
        if (currentFilter != null && !currentFilter.isEmpty()) {
            filterReset();
        }

        ColumnSettings columnSettings = displayerSettings.getColumnSettings(firstColumn);
        String firstColumnName = columnSettings.getColumnName();
        String selected = view.getSelectedId();
        if (selected != null) {
            // Filter by the selected value (if any)
            filterUpdate(firstColumnId, Integer.parseInt(selected));
            view.showResetHint(firstColumnName);
        } else {
            view.showSelectHint(firstColumnName);
        }
    }

    // KEEP IN SYNC THE CURRENT SELECTION WITH ANY EXTERNAL FILTER

    @Override
    public void onFilterEnabled(Displayer displayer, DataSetGroup groupOp) {
        String firstColumnId = dataSet.getColumnByIndex(0).getId();
        List<Integer> currentFilter = super.filterIndexes(firstColumnId);

        // If selector is active then ignore external filters.
        if (currentFilter.isEmpty()) {
            if (firstColumnId.equals(groupOp.getColumnGroup().getColumnId())) {
                columnSelectionMap.put(groupOp.getColumnGroup().getColumnId(), groupOp.getSelectedIntervalList());
            }
            super.onFilterEnabled(displayer, groupOp);
        }
    }

    @Override
    public void onFilterReset(Displayer displayer, List<DataSetGroup> groupOps) {
        String firstColumnId = dataSet.getColumnByIndex(0).getId();
        List<Integer> currentFilter = super.filterIndexes(firstColumnId);

        // If selector is active then ignore external filters.
        if (currentFilter.isEmpty()) {
            for (DataSetGroup groupOp : groupOps) {
                if (firstColumnId.equals(groupOp.getColumnGroup().getColumnId())) {
                    columnSelectionMap.remove(groupOp.getColumnGroup().getColumnId());
                }
            }
            super.onFilterReset(displayer, groupOps);
        }
    }
}
