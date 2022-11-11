package main.frontend.ui.table.model;

public interface ModelObjectAdapter<T> {
    Object getColumnFromObject(T obj, int idx);
}
