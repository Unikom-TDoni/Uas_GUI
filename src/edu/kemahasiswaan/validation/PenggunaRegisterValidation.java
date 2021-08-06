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
public final class PenggunaRegisterValidation extends Validation implements 
        IFormValidation<Pengguna, Map<Pengguna, Object>>
{
    private final JTextFieldHandler _textFieldHandler;
    
    public PenggunaRegisterValidation(JTextFieldHandler textFieldHandler)
    {
        _textFieldHandler = textFieldHandler;
    }
    
    @Override
    public Map<Pengguna, Object> ValidateForm() 
    {
        String nullField = _textFieldHandler.GetEmptyFieldName();
        if(nullField.length() != 0)
        {
            ShowErrorValidationMessage("Text field " + nullField + "masih kosong silahkan isi terlebih dahulu");   
            return Collections.emptyMap();
        }
        
        String password = _textFieldHandler.GetTextField(Pengguna.Password).getText();
        String passwordValidation = _textFieldHandler.GetTextField(Pengguna.PasswordValidation).getText();
        
        if(password.length() < 8)
        {
            ShowErrorValidationMessage("Maaf password harus memiliki minimal 8 character");   
            return Collections.emptyMap();
        }
        
        if(!passwordValidation.equals(password))
        {
            ShowErrorValidationMessage("Maaf validasi password yang anda masukan tidak sama dengan password anda");   
            return Collections.emptyMap();
        }
            
        return _textFieldHandler.GetTextFields();
    }
}
