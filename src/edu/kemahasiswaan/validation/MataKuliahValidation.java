/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.kemahasiswaan.validation;

import java.util.Map;
import java.util.Collections;
import java.util.AbstractMap;
import edu.kemahasiswaan.handler.*;
import edu.kemahasiswaan.table.MataKuliah;
import edu.kemahasiswaan.validation.interfaces.*;

/**
 *
 * @author Theod
 */
public final class MataKuliahValidation extends Validation implements 
        IFormValidation<MataKuliah, Map<MataKuliah, Object>>,
        ITableValidation<MataKuliah, Map.Entry<MataKuliah,Object>>
{
    private final JTableHandler _tableHandler;
    private final JTextFieldHandler _textFieldHandler;
    
    public MataKuliahValidation(JTableHandler tableHandler, JTextFieldHandler textFieldHandler) 
    {
        _tableHandler = tableHandler;
        _textFieldHandler = textFieldHandler;
    }

    @Override
    public Map<MataKuliah, Object> ValidateForm() 
    {
        String nullField = _textFieldHandler.GetEmptyFieldName();
        if(nullField.length() != 0)
        {
            ShowErrorValidationMessage("Text Field " + nullField + " Masih Kosong Silahkan Isi Terlebih Dahulu");
            return Collections.emptyMap();
        }
        return _textFieldHandler.GetTextFields();
    }
    
    @Override
    public Map.Entry<MataKuliah, Object> ValidateTable()
    {
        int rowIndex = _tableHandler.GetSelectedRowIndex();
        if(!_tableHandler.IsRowValid(rowIndex))
        {
            ShowErrorValidationMessage("Silahkan Select Row Terlebih Dahulu");
            return new AbstractMap.SimpleEntry<>(null,null);
        }
        return new AbstractMap.SimpleEntry<>((MataKuliah)_tableHandler.TableColumnKey, _tableHandler.GetValueAtColumnKey(rowIndex));
    }
}