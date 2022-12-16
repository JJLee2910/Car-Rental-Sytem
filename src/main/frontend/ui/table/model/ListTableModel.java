package main.frontend.ui.table.model;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public abstract class ListTableModel<T> extends AbstractTableModel {
    private final String[] columnNames;
    private final List<T> list = new ArrayList<>();
    private final ModelObjectAdapter<T> objectAdapter;

    /** create a list of table*/
    protected ListTableModel(String[] columnNames, ModelObjectAdapter<T> objectAdapter) {
        this.columnNames = columnNames;
        this.objectAdapter = objectAdapter;
    }

    protected ListTableModel(String[] columnNames, ModelObjectAdapter<T> objectAdapter, List<T> initial) {
        this.columnNames = columnNames;
        this.objectAdapter = objectAdapter;
        list.addAll(initial);
    }

    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }

    @Override
    public int getRowCount() {
        return list.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        T obj = list.get(rowIndex);
        return objectAdapter.getColumnFromObject(obj, columnIndex);
    }

    public void refreshData(List<T> list) {
        this.list.clear();
        this.list.addAll(list);
    }

    public List<T> getModelAt(int... idxs) {
        return Arrays.stream(idxs)
                .filter(idx -> idx < list.size())
                .mapToObj(list::get)
                .collect(Collectors.toList());
    }

    public T getModelAt(int idx) {
        return list.get(idx);
    }

    public List<T> getList() {
        return new ArrayList<>(list);
    }
}
