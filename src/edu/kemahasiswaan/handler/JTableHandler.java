/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.kemahasiswaan.handler;

import java.util.Map;
import java.util.HashMap;
import javax.swing.JTable;
import java.util.LinkedList;
import javax.swing.RowFilter;
import javax.swing.table.TableRowSorter;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Theod
 * @param <T>
 */
public final class JTableHandler<T extends Enum>
{
    private final JTable _table;
    public final T TableColumnKey;
    private final Enum[] _tableEnumConstant;
    private final DefaultTableModel _tableModel;
    private final TableRowSorter _tableRowSorter;
    
    public JTableHandler(JTable table, T tableColumnKey)
    {
        _table = table;
        TableColumnKey = tableColumnKey;
        _tableModel = (DefaultTableModel)table.getModel();
        _tableRowSorter = new TableRowSorter(_tableModel);
        _tableEnumConstant = tableColumnKey.getClass().getEnumConstants();
        _table.setRowSorter(_tableRowSorter);
    }

    public void Load(LinkedList<Map<T, Object>> dataCollection)
    {
        LinkedList<Object> row = new LinkedList<>();
        dataCollection.stream().map((data) -> {
            for (Enum item : _tableEnumConstant)
                row.add(data.get(item));
            return data;
        }).map(_item -> {
            _tableModel.addRow(row.toArray());
            return _item;
        }).forEachOrdered(_item -> {
            row.clear();
        });
    }
    
    public void Add(Map<T, Object> dataCollection)
    {
        Object[] newRow = GenerateNewRow(dataCollection);
        _tableModel.addRow(newRow);
    }
    
    public void Update(Map<T, Object> dataCollection)
    {
        Object dataKey = dataCollection.get(TableColumnKey);
        int rowIndex = SearchRowIndex(dataKey);
        Object[] newRow = GenerateNewRow(dataCollection);
        _tableModel.removeRow(rowIndex);
        _tableModel.insertRow(rowIndex, newRow);
    }
    
    public void Delete(Map<T, Object> dataCollection)
    {
        _tableModel.removeRow(SearchRowIndex(dataCollection.get(TableColumnKey)));
    }
    
    public final boolean IsRowValid(int row)
    {
        return row < _tableModel.getRowCount() && row >= 0;
    }
    
    public Object GetValueAtColumnKey(int row)
    {
        return _tableModel.getValueAt(row, TableColumnKey.ordinal());
    }
    
    public LinkedList<Object> GetColumnValue(T columnKey)
    {
        LinkedList<Object> result = new LinkedList<>();
        for (int i = 0; i < _tableModel.getRowCount(); i++) 
            result.add(_tableModel.getValueAt(i, columnKey.ordinal()));
        return result;
    }
    
    public Map<T, Object> GetRowValue(int rowIndex)
    {
        int columnIndex = 0;
        HashMap<T, Object> result = new HashMap<>();
        for (Enum item : _tableEnumConstant) 
            result.put((T)item, _tableModel.getValueAt(rowIndex, columnIndex++));
        return result;
    }
    
    public int GetSelectedRowIndex()
    {
        return _table.getSelectedRow();
    }
    
    public void FilterTable(String filter)
    {
        _tableRowSorter.setRowFilter(RowFilter.regexFilter("^"+filter+"$", 0));
    }
    
    private Object[] GenerateNewRow(Map<T, Object> keyValueData)
    {
        LinkedList<Object> row = new LinkedList<>();
        for (Enum item : _tableEnumConstant)
            row.add(keyValueData.get(item));
        return row.toArray();
    }
    
    private int SearchRowIndex(Object data)
    {        
        for(int i = 0; i< _tableModel.getRowCount(); i++)
            if(_tableModel.getValueAt(i, TableColumnKey.ordinal()).equals(data))
                return i;
        return -1;
    }
}
