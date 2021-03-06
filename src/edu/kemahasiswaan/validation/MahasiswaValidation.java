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
import edu.kemahasiswaan.table.Mahasiswa;
import edu.kemahasiswaan.validation.interfaces.*;

/**
 *
 * @author Theod
 */
public final class MahasiswaValidation extends Validation implements 
        IFormValidation<Mahasiswa, Map<Mahasiswa, Object>>,
        ITableValidation<Mahasiswa, Map.Entry<Mahasiswa,Object>>
{
    private final JTableHandler _tableHandler;
    private final JTextFieldHandler _textFieldHandler;
    
    public MahasiswaValidation(JTableHandler tableHandler, JTextFieldHandler textFieldHandler) 
    {
        _tableHandler = tableHandler;
        _textFieldHandler = textFieldHandler;
    }

    @Override
    public Map<Mahasiswa, Object> ValidateForm() 
    {
        String nullField = _textFieldHandler.GetEmptyFieldName();
        if(nullField.length() != 0)
        {
            ShowErrorValidationMessage("Maaf, text field " + nullField + "masih kosong silahkan isi terlebih fahulu");
            return Collections.emptyMap();
        }
        return _textFieldHandler.GetTextFields();
    }
    
    @Override
    public Map.Entry<Mahasiswa, Object> ValidateTable()
    {
        int rowIndex = _tableHandler.GetSelectedRowIndex();
        if(!_tableHandler.IsRowValid(rowIndex))
        {
            ShowErrorValidationMessage("Maaf, silahkan pilih baris pada tabel Mahasiswa terlebih dahulu");
            return new AbstractMap.SimpleEntry<>(null,null);
        }
        return new AbstractMap.SimpleEntry<>((Mahasiswa)_tableHandler.TableColumnKey, _tableHandler.GetValueAt(_tableHandler.TableColumnKey, rowIndex));
    }
}
