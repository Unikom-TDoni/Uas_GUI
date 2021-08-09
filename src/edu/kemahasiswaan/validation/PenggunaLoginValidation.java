/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.kemahasiswaan.validation;

import java.util.Map;
import java.util.Collections;
import edu.kemahasiswaan.table.Pengguna;
import edu.kemahasiswaan.handler.JTextFieldHandler;
import edu.kemahasiswaan.validation.interfaces.IFormValidation;

/**
 *
 * @author Theod
 */
public final class PenggunaLoginValidation extends Validation implements 
        IFormValidation<Pengguna, Map<Pengguna, Object>>
{
    private final JTextFieldHandler _textFieldHandler;
    
    public PenggunaLoginValidation(JTextFieldHandler textFieldHandler)
    {
        _textFieldHandler = textFieldHandler;
    }
    
    @Override
    public Map<Pengguna, Object> ValidateForm() 
    {
        String nullField = _textFieldHandler.GetEmptyFieldName();
        if(nullField.length() != 0)
        {
            ShowErrorValidationMessage("Maaf, text field " + nullField + "masih kosong silahkan isi terlebih dahulu");   
            return Collections.emptyMap();
        }
        return _textFieldHandler.GetTextFields();
    }
}
