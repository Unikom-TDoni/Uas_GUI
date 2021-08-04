/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.kemahasiswaan.controller;

import java.sql.SQLException;
import javax.swing.JOptionPane;
import edu.kemahasiswaan.repository.PenggunaRepository;
import edu.kemahasiswaan.response.PenggunaResponse;
import edu.kemahasiswaan.validation.PenggunaLoginValidation;
import edu.kemahasiswaan.validation.PenggunaRegisterValidation;

/**
 *
 * @author Theod
 */
public final class PenggunaController extends Controller<PenggunaRepository>
{
    private final PenggunaLoginValidation _loginValidation;
    private final PenggunaRegisterValidation _registerValidation;
    
    public PenggunaController(PenggunaLoginValidation loginValidation, PenggunaRegisterValidation registerValidation)
    {
        _loginValidation = loginValidation;
        _registerValidation = registerValidation;
        Repository = new PenggunaRepository();
    }
    
    public PenggunaResponse CheckLogin()
    {
        try
        {
            var validationResult = _loginValidation.ValidateForm();
            var loginResult = Repository.GetLoginAttempt(validationResult);
            return new PenggunaResponse().GenerateResultFromLoginStatus(loginResult);
        }
        catch(SQLException exception)
        {
            JOptionPane.showMessageDialog(null, 
                exception.getMessage(), "Error", 
                JOptionPane.INFORMATION_MESSAGE
            );
            return null;
        }
    }
    
    public PenggunaResponse Register()
    {
        try
        {
            var validationResult = _registerValidation.ValidateForm();
            if(validationResult.isEmpty()) return null;
            Repository.Create(validationResult);
            return new PenggunaResponse().GenerateResultFromValidation(validationResult);
        }
        catch(SQLException exception)
        {
            JOptionPane.showMessageDialog(null, 
                exception.getMessage(), "Error", 
                JOptionPane.INFORMATION_MESSAGE
            );
            return null;
        }
    }
    
}
