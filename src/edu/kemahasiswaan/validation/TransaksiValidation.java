/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.kemahasiswaan.validation;

import java.util.Map;
import java.util.HashMap;
import java.util.AbstractMap;
import java.util.Collections;
import edu.kemahasiswaan.table.Transaksi;
import edu.kemahasiswaan.helper.DateHelper;
import edu.kemahasiswaan.handler.JTableHandler;
import edu.kemahasiswaan.handler.JComboBoxHandler;
import edu.kemahasiswaan.handler.JTextFieldHandler;
import edu.kemahasiswaan.validation.interfaces.IFormValidation;
import edu.kemahasiswaan.validation.interfaces.ITableValidation;

/**
 *
 * @author Theod
 */
public final class TransaksiValidation extends Validation implements 
        IFormValidation<Transaksi, Map<Transaksi,Object>>, 
        ITableValidation<Transaksi, Map.Entry<Transaksi, Object>>{

    private final JTableHandler _tableHandler;
    private final JComboBoxHandler _comboBoxHandler;
    private final JTextFieldHandler _textFieldHandler;
    
    public TransaksiValidation(JTableHandler tableHandler, JTextFieldHandler textFieldHandler, JComboBoxHandler comboBoxHandler)
    {
        _tableHandler = tableHandler;
        _comboBoxHandler = comboBoxHandler;
        _textFieldHandler = textFieldHandler;
    }
    
    
    @Override
    public Map<Transaksi, Object> ValidateForm() 
    {
        String nullField = _textFieldHandler.GetEmptyFieldName();
        if(nullField.length() != 0)
        {
            ShowErrorValidationMessage("Text field " + nullField + "masih kosong silahkan isi terlebih dahulu");
            return Collections.emptyMap();
        }
        
        Map<Transaksi, Object> result = new HashMap<>(_textFieldHandler.GetTextFields());
        result.put(Transaksi.MetodePembayaran, _comboBoxHandler.GetSelectedItem(Transaksi.MetodePembayaran));
        result.put(Transaksi.Nominal, _comboBoxHandler.GetSelectedItem(Transaksi.Nominal));
        result.put(Transaksi.Operator, _comboBoxHandler.GetSelectedItem(Transaksi.Operator));
        result.put(Transaksi.Status, _comboBoxHandler.GetSelectedItem(Transaksi.Status));
        result.put(Transaksi.Tanggal, DateHelper.GetCurrentDate());
        result.put(Transaksi.id, _tableHandler.GetValueAt(Transaksi.id, _tableHandler.GetSelectedRowIndex()));
        return result;
    }
    
    public Map<Transaksi, Object> validateFormCreate(){
        String nullField = _textFieldHandler.GetEmptyFieldName();
        if(nullField.length() != 0)
        {
            ShowErrorValidationMessage("Text field " + nullField + "masih kosong silahkan isi terlebih dahulu");
            return Collections.emptyMap();
        }
        
        Map<Transaksi, Object> result = new HashMap<>(_textFieldHandler.GetTextFields());
        result.put(Transaksi.MetodePembayaran, _comboBoxHandler.GetSelectedItem(Transaksi.MetodePembayaran));
        result.put(Transaksi.Nominal, _comboBoxHandler.GetSelectedItem(Transaksi.Nominal));
        result.put(Transaksi.Operator, _comboBoxHandler.GetSelectedItem(Transaksi.Operator));
        result.put(Transaksi.Status, _comboBoxHandler.GetSelectedItem(Transaksi.Status));
        result.put(Transaksi.Tanggal, DateHelper.GetCurrentDate());
        return result;
    }
    

    public Map<Transaksi, Object> ValidateFormUpdate(){
        String nullField = _textFieldHandler.GetEmptyFieldName();
        if(nullField.length() != 0)
        {
            ShowErrorValidationMessage("Text field " + nullField + "masih kosong silahkan isi terlebih dahulu");
            return Collections.emptyMap();
        }
        
        Map<Transaksi, Object> result = new HashMap<>(_textFieldHandler.GetTextFields());
        result.put(Transaksi.id, _tableHandler.GetValueAt(Transaksi.id, _tableHandler.GetSelectedRowIndex()));
        result.put(Transaksi.MetodePembayaran, _comboBoxHandler.GetSelectedItem(Transaksi.MetodePembayaran));
        result.put(Transaksi.Nominal, _comboBoxHandler.GetSelectedItem(Transaksi.Nominal));
        result.put(Transaksi.Operator, _comboBoxHandler.GetSelectedItem(Transaksi.Operator));
        result.put(Transaksi.Status, _comboBoxHandler.GetSelectedItem(Transaksi.Status));
        return result;
    }
    
    @Override
    public Map.Entry<Transaksi, Object> ValidateTable() 
    {
        int rowIndex = _tableHandler.GetSelectedRowIndex();
        if(!_tableHandler.IsRowValid(rowIndex))
        {
            ShowErrorValidationMessage("Silahkan pilih baris table terlebih dahulu");
            return new AbstractMap.SimpleEntry<>(null, null);
        }
        return new AbstractMap.SimpleEntry<>((Transaksi)_tableHandler.TableColumnKey, _tableHandler.GetValueAt(_tableHandler.TableColumnKey, rowIndex));
    }
    
}
