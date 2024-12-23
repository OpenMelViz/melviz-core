package org.melviz.dataset.backend;

import java.util.List;

import org.melviz.dataset.def.DataColumnDef;
import org.melviz.dataset.def.DataSetDef;

/**
 * <p>Response model for a DataSetDef edition.</p>
 * <p>Provides a cloned DataSetDef instance from the original one and the original column definition list.</p>
 */
public class EditDataSetDef {

    private DataSetDef definition;
    private List<DataColumnDef> columns;

    public EditDataSetDef() {
        
    }

    public EditDataSetDef(final DataSetDef definition, final List<DataColumnDef> columns) {
        this.definition = definition;
        this.columns = columns;
    }

    public DataSetDef getDefinition() {
        return definition;
    }

    public List<DataColumnDef> getColumns() {
        return columns;
    }
    
}
