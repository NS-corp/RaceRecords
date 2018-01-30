package com.company;

import javax.swing.table.DefaultTableModel;
import java.util.Arrays;
import java.util.Vector;

public class TableModel extends DefaultTableModel {
    private Vector<?> headers;

    public TableModel(Vector<? extends Vector> data, Vector<?> columnNames) {
        super(data, columnNames);
        headers = columnNames;
    }

    public TableModel(Object[][] data, Object[] columnNames) {
        super(data, columnNames);
        headers = new Vector<>(Arrays.asList(columnNames));
    }

    public Vector<?> getHeaders() {
        return headers;
    }

    public void setHeaders(Vector<?> headers) {
        this.headers = headers;
    }
}
