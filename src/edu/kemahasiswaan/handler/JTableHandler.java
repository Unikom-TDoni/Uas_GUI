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
        table.getTableHeader().setReorderingAllowed(false);
        _table = table;
        TableColumnKey = tableColumnKey;
        _tableModel = (DefaultTableModel)table.getModel();
        _tableRowSorter = new TableRowSorter(_tableModel);
        _tableEnumConstant = tableColumnKey.getClass().getEnumConstants();
        for (int i = 0; i < table.getColumnCount(); i++)
            _tableRowSorter.setSortable(i, false);
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
        int rowIndex = GetRowIndex(TableColumnKey, dataKey);
        Object[] newRow = GenerateNewRow(dataCollection);
        _tableModel.removeRow(rowIndex);
        _tableModel.insertRow(rowIndex, newRow);
    }
    
    public void Delete(Map<T, Object> dataCollection)
    {
        _tableModel.removeRow(GetRowIndex(TableColumnKey, dataCollection.get(TableColumnKey)));
    }
    
    public final boolean IsRowValid(int row)
    {
        return row < _tableModel.getRowCount() && row >= 0;
    }
    
    public Object GetValueAt(T columnKey, int row)
    {
        return _tableModel.getValueAt(row, columnKey.ordinal());
    }
    
    public int GetRowIndex(T columnKey, Object data)
    {        
        for(int i = 0; i< _tableModel.getRowCount(); i++)
            if(_tableModel.getValueAt(i, columnKey.ordinal()).equals(data))
                return i;
        return -1;
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
        HashMap<T, Object> result = new HashMap<>();
        for (int i = 0; i < _table.getColumnCount(); i++)
            result.put((T)_tableEnumConstant[i], _tableModel.getValueAt(rowIndex, i));
        return result;
    }
    
    public int GetSelectedRowIndex()
    {
        return _table.getSelectedRow();
    }
    
    public void FilterTable(String filter, T... column)
    {
        if(filter.trim().isEmpty()){
            _tableRowSorter.setRowFilter(null);
            return;
        }
        
        _tableRowSorter.setRowFilter(RowFilter.regexFilter(filter, column[0].ordinal(), column[1].ordinal()));
    }
    
    private Object[] GenerateNewRow(Map<T, Object> keyValueData)
    {
        LinkedList<Object> row = new LinkedList<>();
        for (Enum item : _tableEnumConstant)
            row.add(keyValueData.get(item));
        return row.toArray();
    }
}
