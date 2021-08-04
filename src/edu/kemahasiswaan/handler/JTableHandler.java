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
        _tableModel = (DefaultTableModel) table.getModel();
        _tableRowSorter = new TableRowSorter(_tableModel);
        _table.setRowSorter(_tableRowSorter);
        _tableEnumConstant = tableColumnKey.getClass().getEnumConstants();
    }

    public void Load(LinkedList<Map<T, Object>> dataCollection)
    {
        var row = new LinkedList<>();
        dataCollection.stream().map((data) -> {
            for (var item : _tableEnumConstant)
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
        var newRow = GenerateNewRow(dataCollection);
        _tableModel.addRow(newRow);
    }
    
    public void Update(Map<T, Object> dataCollection)
    {
        var dataKey = dataCollection.get(TableColumnKey);
        var rowIndex = SearchRowIndex(dataKey);
        var newRow = GenerateNewRow(dataCollection);
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
    
    public Map<T, Object> GetRow(int row)
    {
        var columnIndex = 0;
        var result = new HashMap<T, Object>();
        for (var item : _tableEnumConstant) 
            result.put((T)item, _tableModel.getValueAt(row, columnIndex++));
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
        var row = new LinkedList<>();
        for (var item : _tableEnumConstant)
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
