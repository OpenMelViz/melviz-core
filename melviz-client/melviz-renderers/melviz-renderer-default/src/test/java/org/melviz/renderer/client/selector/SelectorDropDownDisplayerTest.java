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
package org.melviz.renderer.client.selector;

import java.util.Arrays;
import java.util.List;

import org.jboss.errai.ioc.client.container.SyncBeanDef;
import org.jboss.errai.ioc.client.container.SyncBeanManager;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.melviz.dataset.DataSet;
import org.melviz.dataset.date.DayOfWeek;
import org.melviz.dataset.date.Month;
import org.melviz.dataset.filter.FilterFactory;
import org.melviz.dataset.group.AggregateFunctionType;
import org.melviz.dataset.group.DataSetGroup;
import org.melviz.dataset.group.DateIntervalType;
import org.melviz.dataset.group.Interval;
import org.melviz.displayer.DisplayerSettings;
import org.melviz.displayer.DisplayerSettingsFactory;
import org.melviz.displayer.client.AbstractDisplayerTest;
import org.melviz.displayer.client.DisplayerListener;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.melviz.dataset.ExpenseReportsData.COLUMN_DATE;
import static org.melviz.dataset.ExpenseReportsData.COLUMN_DEPARTMENT;
import static org.melviz.dataset.ExpenseReportsData.COLUMN_ID;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.Silent.class)
public class SelectorDropDownDisplayerTest extends AbstractDisplayerTest {

    @Mock
    SyncBeanManager beanManager;

    @Mock
    SyncBeanDef<SelectorDropDownItem> dropDownItemBean;

    @Mock
    SelectorDropDownItem dropDownItem;

    public SelectorDropDownDisplayer createSelectorDisplayer(DisplayerSettings settings) {
        return initDisplayer(new SelectorDropDownDisplayer(mock(SelectorDropDownDisplayer.View.class), beanManager), settings);
    }

    @Before
    public void setUp() {
        when(beanManager.lookupBean(SelectorDropDownItem.class)).thenReturn(dropDownItemBean);
        when(dropDownItemBean.newInstance()).thenReturn(dropDownItem);
    }

    @Test
    public void testDraw() {
        DisplayerSettings departmentList = DisplayerSettingsFactory.newSelectorSettings()
                .dataset(EXPENSES)
                .group(COLUMN_DEPARTMENT)
                .column(COLUMN_DEPARTMENT)
                .column(COLUMN_ID, AggregateFunctionType.COUNT)
                .filterOn(false, true, false)
                .multiple(false)
                .buildSettings();

        SelectorDropDownDisplayer presenter = createSelectorDisplayer(departmentList);
        SelectorDropDownDisplayer.View view = presenter.getView();
        presenter.draw();

        verify(view).clearItems();
        verify(view).showSelectHint(COLUMN_DEPARTMENT, false);
        verify(view, times(5)).addItem(any());
        verify(view, never()).showResetHint(anyString(), anyBoolean());

        // Verify the entries are sorted by default
        DataSet dataSet = presenter.getDataSetHandler().getLastDataSet();
        assertEquals(dataSet.getValueAt(0, 0), "Engineering");
        assertEquals(dataSet.getValueAt(4, 0), "Support");
    }

    @Test
    public void testNoData() {
        DisplayerSettings departmentList = DisplayerSettingsFactory.newSelectorSettings()
                .dataset(EXPENSES)
                .filter(COLUMN_ID, FilterFactory.isNull())
                .group(COLUMN_DEPARTMENT)
                .column(COLUMN_DEPARTMENT)
                .column(COLUMN_ID, AggregateFunctionType.COUNT)
                .multiple(false)
                .buildSettings();

        SelectorDropDownDisplayer presenter = createSelectorDisplayer(departmentList);
        SelectorDropDownDisplayer.View view = presenter.getView();
        presenter.draw();

        verify(view).clearItems();
        verify(view).showSelectHint(COLUMN_DEPARTMENT, false);
        verify(view, never()).addItem(any());
    }

    @Test
    public void testNullNotShown() {
        DisplayerSettings departmentList = DisplayerSettingsFactory.newSelectorSettings()
                .dataset(EXPENSES)
                .group(COLUMN_DEPARTMENT)
                .column(COLUMN_DEPARTMENT)
                .column(COLUMN_ID, AggregateFunctionType.COUNT)
                .buildSettings();

        // Insert a null entry into the dataset
        DataSet expensesDataSet = clientDataSetManager.getDataSet(EXPENSES);
        int column = expensesDataSet.getColumnIndex(expensesDataSet.getColumnById(COLUMN_DEPARTMENT));
        expensesDataSet.setValueAt(0, column, null);

        // ... and make sure it's not shown
        SelectorDropDownDisplayer presenter = createSelectorDisplayer(departmentList);
        SelectorDropDownDisplayer.View view = presenter.getView();
        presenter.draw();

        verify(view, times(5)).addItem(any());
        verify(view, never()).addItem(null);
    }

    @Test
    public void testSelectDisabled() {
        DisplayerSettings departmentList = DisplayerSettingsFactory.newSelectorSettings()
                .dataset(EXPENSES)
                .group(COLUMN_DEPARTMENT)
                .column(COLUMN_DEPARTMENT)
                .column(COLUMN_ID, AggregateFunctionType.COUNT)
                .filterOff(true)
                .buildSettings();

        SelectorDropDownDisplayer presenter = createSelectorDisplayer(departmentList);
        DisplayerListener listener = mock(DisplayerListener.class);
        SelectorDropDownDisplayer.View view = presenter.getView();
        presenter.draw();

        reset(view);
        presenter.addListener(listener);
        presenter.onItemSelected(dropDownItem);

        // Check filter notifications
        verify(listener, never()).onFilterEnabled(eq(presenter), any(DataSetGroup.class));
        verify(listener, never()).onRedraw(presenter);

        // Ensure data does not change
        verify(view, never()).showResetHint(COLUMN_DEPARTMENT, true);
        verify(view, never()).clearItems();
        verify(view, never()).showSelectHint(COLUMN_DEPARTMENT, true);
        verify(view, never()).addItem(any());
    }

    @Test
    public void testSelectItem() {
        DisplayerSettings departmentList = DisplayerSettingsFactory.newSelectorSettings()
                .dataset(EXPENSES)
                .group(COLUMN_DEPARTMENT)
                .column(COLUMN_DEPARTMENT)
                .column(COLUMN_ID, AggregateFunctionType.COUNT)
                .filterOn(false, true, true)
                .multiple(false)
                .buildSettings();

        SelectorDropDownDisplayer presenter = createSelectorDisplayer(departmentList);
        SelectorDropDownDisplayer.View view = presenter.getView();
        DisplayerListener listener = mock(DisplayerListener.class);
        presenter.draw();

        // Select an item
        reset(view);
        when(dropDownItem.getId()).thenReturn(1);
        presenter.addListener(listener);
        presenter.onItemSelected(dropDownItem);

        // Ensure data does not change
        verify(view, never()).clearItems();
        verify(view, never()).addItem(any());

        // Verify the item selected is correct
        ArgumentCaptor<DataSetGroup> argument = ArgumentCaptor.forClass(DataSetGroup.class);
        verify(view).showResetHint(COLUMN_DEPARTMENT, false);
        verify(view, never()).showSelectHint(COLUMN_DEPARTMENT, false);
        verify(listener).onFilterEnabled(eq(presenter), argument.capture());
        verify(listener, never()).onRedraw(presenter);
        DataSetGroup dataSetGroup = argument.getValue();
        List<Interval> selectedIntervals = dataSetGroup.getSelectedIntervalList();
        assertEquals(selectedIntervals.size(), 1);
        Interval selectedInterval = selectedIntervals.get(0);
        assertEquals(selectedInterval.getName(), "Management");
    }

    @Test
    public void testMultipleSelect() {
        DisplayerSettings departmentList = DisplayerSettingsFactory.newSelectorSettings()
                .dataset(EXPENSES)
                .group(COLUMN_DEPARTMENT)
                .column(COLUMN_DEPARTMENT)
                .column(COLUMN_ID, AggregateFunctionType.COUNT)
                .filterOn(false, true, true)
                .multiple(true)
                .width(100)
                .buildSettings();

        SelectorDropDownDisplayer presenter = createSelectorDisplayer(departmentList);
        SelectorDropDownDisplayer.View view = presenter.getView();
        DisplayerListener listener = mock(DisplayerListener.class);
        presenter.draw();

        // Select an item
        presenter.addListener(listener);
        when(dropDownItem.getId()).thenReturn(1);
        presenter.onItemSelected(dropDownItem);

        // Select another item
        reset(view);
        reset(listener);
        when(dropDownItem.getId()).thenReturn(2);
        presenter.onItemSelected(dropDownItem);

        // Ensure data does not change
        verify(view, never()).clearItems();
        verify(view, never()).addItem(any());

        // Verify the item selected is correct
        ArgumentCaptor<DataSetGroup> argument = ArgumentCaptor.forClass(DataSetGroup.class);
        verify(view).showResetHint(COLUMN_DEPARTMENT, true);
        verify(view, never()).showSelectHint(COLUMN_DEPARTMENT, true);
        verify(view).showCurrentSelection("Management ...", "Management, Sales ");
        verify(listener).onFilterEnabled(eq(presenter), argument.capture());
        verify(listener, never()).onRedraw(presenter);
        DataSetGroup dataSetGroup = argument.getValue();
        List<Interval> selectedIntervals = dataSetGroup.getSelectedIntervalList();
        assertEquals(selectedIntervals.size(), 2);
        Interval selectedInterval1 = selectedIntervals.get(0);
        Interval selectedInterval2 = selectedIntervals.get(1);
        assertEquals(selectedInterval1.getName(), "Management");
        assertEquals(selectedInterval2.getName(), "Sales");

    }

    @Test
    public void testFormatItemList() {
        DisplayerSettings departmentList = DisplayerSettingsFactory.newSelectorSettings()
                .dataset(EXPENSES)
                .group(COLUMN_DEPARTMENT)
                .column(COLUMN_DEPARTMENT)
                .column(COLUMN_ID, AggregateFunctionType.COUNT)
                .filterOn(false, true, true)
                .multiple(true)
                .width(90)
                .buildSettings();

        SelectorDropDownDisplayer presenter = createSelectorDisplayer(departmentList);

        String result = presenter.formatItemList(Arrays.asList("Sales", "Management", "IT"), 200);
        assertEquals(result, "Sales, Management, IT ");

        result = presenter.formatItemList(Arrays.asList("Sales", "Management", "IT"));
        assertEquals(result, "Sales, Management, IT ");

        result = presenter.formatItemList(Arrays.asList("Sales", "Management", "IT"), 150);
        assertEquals(result, "Sales, Managemen...");

        result = presenter.formatItemList(Arrays.asList("Sales", "Management", "IT"), 130);
        assertEquals(result, "Sales, Managem...");

        result = presenter.formatItemList(Arrays.asList("Sales", "Management", "IT"), 60);
        assertEquals(result, "Sales ...");

        result = presenter.formatItemList(Arrays.asList("Sales", "Management", "IT"), 10);
        assertEquals(result, "S...");
    }

    @Test
    public void testDrillDown() {
        DisplayerSettings departmentList = DisplayerSettingsFactory.newSelectorSettings()
                .dataset(EXPENSES)
                .group(COLUMN_DEPARTMENT)
                .column(COLUMN_DEPARTMENT)
                .column(COLUMN_ID, AggregateFunctionType.COUNT)
                .filterOn(true, true, true)
                .multiple(false)
                .buildSettings();

        SelectorDropDownDisplayer presenter = createSelectorDisplayer(departmentList);
        SelectorDropDownDisplayer.View view = presenter.getView();
        DisplayerListener listener = mock(DisplayerListener.class);
        presenter.draw();

        reset(view);
        when(dropDownItem.getId()).thenReturn(1);
        presenter.addListener(listener);
        presenter.onItemSelected(dropDownItem);

        // Check filter notifications
        verify(listener).onFilterEnabled(eq(presenter), any(DataSetGroup.class));
        verify(listener).onRedraw(presenter);

        // Check selector refreshes
        verify(view).clearItems();
        verify(view, atLeastOnce()).showResetHint(COLUMN_DEPARTMENT, false);
        verify(view, never()).showSelectHint(COLUMN_DEPARTMENT, false);
        verify(view, times(1)).addItem(any());
    }

    @Test
    public void testNullEntries() {
        // Insert a null entry into the dataset
        DataSet expensesDataSet = clientDataSetManager.getDataSet(EXPENSES);
        int column = expensesDataSet.getColumnIndex(expensesDataSet.getColumnById(COLUMN_DEPARTMENT));
        expensesDataSet.setValueAt(0, column, null);

        // Create a selector displayer
        DisplayerSettings departmentList = DisplayerSettingsFactory.newSelectorSettings()
                .dataset(EXPENSES)
                .group(COLUMN_DEPARTMENT)
                .column(COLUMN_DEPARTMENT)
                .column(COLUMN_ID, AggregateFunctionType.COUNT)
                .filterOn(false, true, true)
                .buildSettings();

        SelectorDropDownDisplayer presenter = createSelectorDisplayer(departmentList);
        SelectorDropDownDisplayer.View view = presenter.getView();
        DisplayerListener listener = mock(DisplayerListener.class);
        presenter.addListener(listener);
        presenter.draw();

        // Verify that null entries are not shown
        verify(view, times(5)).addItem(any());
        verify(view, never()).addItem(null);

        // Select an item
        reset(listener);
        when(dropDownItem.getId()).thenReturn(1);
        presenter.onItemSelected(dropDownItem);

        // Verify the item selected is correct
        ArgumentCaptor<DataSetGroup> argument = ArgumentCaptor.forClass(DataSetGroup.class);
        verify(listener).onFilterEnabled(eq(presenter), argument.capture());
        DataSetGroup dataSetGroup = argument.getValue();
        Interval selectedInterval = dataSetGroup.getSelectedIntervalList().get(0);
        assertEquals(selectedInterval.getName(), "Engineering");
    }

    @Test
    public void testSortFixedMonthDefault() {
        DisplayerSettings displayerSettings = DisplayerSettingsFactory.newSelectorSettings()
                .dataset(EXPENSES)
                .group(COLUMN_DATE).fixed(DateIntervalType.MONTH, true)
                .column(COLUMN_DATE)
                .column(COLUMN_ID, AggregateFunctionType.COUNT)
                .buildSettings();

        SelectorDropDownDisplayer presenter = createSelectorDisplayer(displayerSettings);
        presenter.draw();
        DataSet dataSet = presenter.getDataSetHandler().getLastDataSet();
        assertEquals(dataSet.getValueAt(0, 0), "1");
        assertEquals(dataSet.getValueAt(11, 0), "12");
    }

    @Test
    public void testSortFixedFirstMonth() {
        DisplayerSettings displayerSettings = DisplayerSettingsFactory.newSelectorSettings()
                .dataset(EXPENSES)
                .group(COLUMN_DATE).fixed(DateIntervalType.MONTH, true).firstMonth(Month.FEBRUARY)
                .column(COLUMN_DATE)
                .column(COLUMN_ID, AggregateFunctionType.COUNT)
                .buildSettings();

        SelectorDropDownDisplayer presenter = createSelectorDisplayer(displayerSettings);
        presenter.draw();
        DataSet dataSet = presenter.getDataSetHandler().getLastDataSet();
        assertEquals(dataSet.getValueAt(0, 0), "2");
        assertEquals(dataSet.getValueAt(11, 0), "1");
    }

    @Test
    public void testSortFixedDayOfWeekDefault() {
        DisplayerSettings displayerSettings = DisplayerSettingsFactory.newSelectorSettings()
                .dataset(EXPENSES)
                .group(COLUMN_DATE).fixed(DateIntervalType.DAY_OF_WEEK, true)
                .column(COLUMN_DATE)
                .column(COLUMN_ID, AggregateFunctionType.COUNT)
                .buildSettings();

        SelectorDropDownDisplayer presenter = createSelectorDisplayer(displayerSettings);
        presenter.draw();
        DataSet dataSet = presenter.getDataSetHandler().getLastDataSet();
        assertEquals(dataSet.getValueAt(0, 0), "2");
        assertEquals(dataSet.getValueAt(6, 0), "1");
    }

    @Test
    public void testSortFixedFirstDayOfWeek() {
        DisplayerSettings displayerSettings = DisplayerSettingsFactory.newSelectorSettings()
                .dataset(EXPENSES)
                .group(COLUMN_DATE).fixed(DateIntervalType.DAY_OF_WEEK, true).firstDay(DayOfWeek.SUNDAY)
                .column(COLUMN_DATE)
                .column(COLUMN_ID, AggregateFunctionType.COUNT)
                .buildSettings();

        SelectorDropDownDisplayer presenter = createSelectorDisplayer(displayerSettings);
        presenter.draw();
        DataSet dataSet = presenter.getDataSetHandler().getLastDataSet();
        assertEquals(dataSet.getValueAt(0, 0), "1");
        assertEquals(dataSet.getValueAt(6, 0), "7");
    }

    @Test
    public void testSortFixedHour() {
        DisplayerSettings displayerSettings = DisplayerSettingsFactory.newSelectorSettings()
                .dataset(EXPENSES)
                .group(COLUMN_DATE).fixed(DateIntervalType.HOUR, true)
                .column(COLUMN_DATE)
                .column(COLUMN_ID, AggregateFunctionType.COUNT)
                .buildSettings();

        SelectorDropDownDisplayer presenter = createSelectorDisplayer(displayerSettings);
        presenter.draw();
        DataSet dataSet = presenter.getDataSetHandler().getLastDataSet();
        assertEquals(dataSet.getValueAt(0, 0), "0");
        assertEquals(dataSet.getValueAt(23, 0), "23");
    }

    @Test
    public void testSortFixedMinute() {
        DisplayerSettings displayerSettings = DisplayerSettingsFactory.newSelectorSettings()
                .dataset(EXPENSES)
                .group(COLUMN_DATE).fixed(DateIntervalType.MINUTE, true)
                .column(COLUMN_DATE)
                .column(COLUMN_ID, AggregateFunctionType.COUNT)
                .buildSettings();

        SelectorDropDownDisplayer presenter = createSelectorDisplayer(displayerSettings);
        presenter.draw();
        DataSet dataSet = presenter.getDataSetHandler().getLastDataSet();
        assertEquals(dataSet.getValueAt(0, 0), "0");
        assertEquals(dataSet.getValueAt(59, 0), "59");
    }

    @Test
    public void testSortFixedSecond() {
        DisplayerSettings displayerSettings = DisplayerSettingsFactory.newSelectorSettings()
                .dataset(EXPENSES)
                .group(COLUMN_DATE).fixed(DateIntervalType.SECOND, true)
                .column(COLUMN_DATE)
                .column(COLUMN_ID, AggregateFunctionType.COUNT)
                .buildSettings();

        SelectorDropDownDisplayer presenter = createSelectorDisplayer(displayerSettings);
        presenter.draw();
        DataSet dataSet = presenter.getDataSetHandler().getLastDataSet();
        assertEquals(dataSet.getValueAt(0, 0), "0");
        assertEquals(dataSet.getValueAt(59, 0), "59");
    }
}