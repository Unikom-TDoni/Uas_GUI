/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.kemahasiswaan.handler;

import javax.swing.JTable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.function.Supplier;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Theod
 */
public final class TableHandler 
{
    private final DefaultTableModel _tableModel;
    private final Supplier<Integer> _selectedRowSupplier;

    public TableHandler(JTable table)
    {
        _tableModel = (DefaultTableModel) table.getModel();
        _selectedRowSupplier = () -> table.getSelectedRow();
    }
    
    public void LoadData(ResultSet resultSet)
    {
        try
        {
            var resultSetMetaData = resultSet.getMetaData();
            var columnCount = resultSetMetaData.getColumnCount();
            var data = new String[columnCount];
            while (resultSet.next())
            {
                for (int i = 0; i < columnCount; i++) 
                    data[i] = resultSet.getString(i+1);
                _tableModel.addRow(data);
            }
        }
        catch(SQLException exception)
        {
        
        }
    }
    
    public void UpdateData(Object... data)
    {
        var row = _selectedRowSupplier.get();
        _tableModel.removeRow(row);
        _tableModel.insertRow(row, data);
    }
    
    public void AddData(Object... data)
    {
        _tableModel.insertRow(0, data);
    }
}
