package raf.project.desktop_view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;
import java.util.List;

public class ResultTable extends JTable {

    public ResultTable() {
        // Initialize the table with an empty default model
        setModel(new DefaultTableModel());
    }

    public ResultTable displayResultSet(List<List<String>> resultSet) {
        DefaultTableModel model = (DefaultTableModel) getModel();

        List<List<String>> test = new ArrayList<>();
        test.add(List.of("kolona1", "kolona2", "kolona3"));
        test.add(List.of("mcica", "zika", "a"));
        test.add(List.of("aa", "bb", "dd"));
        resultSet  = test;

        // Clear existing table data
        model.setRowCount(0);
        model.setColumnCount(0);

        if (resultSet != null && !resultSet.isEmpty()) {
            List<String> columnNames = resultSet.get(0);

            // Set the column names
            for (String columnName : columnNames) {
                model.addColumn(columnName);
            }

            // Set the row data
            for (int i = 1; i < resultSet.size(); i++) {
                List<String> rowData = resultSet.get(i);
                model.addRow(rowData.toArray());
            }
        }
        return this;
    }
}