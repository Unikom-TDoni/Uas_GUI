/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.kemahasiswaan.controller;

import java.util.Map;
import java.sql.SQLException;
import edu.kemahasiswaan.table.Pengguna;
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
        Repository = new PenggunaRepository();
        _registerValidation = registerValidation;
    }
    
    public PenggunaResponse CheckLogin()
    {
        try
        {
            Map<Pengguna, Object> validationResult = _loginValidation.ValidateForm();
            if(validationResult.isEmpty()) return null;
            boolean loginResult = Repository.IsLoginSuccess(validationResult);
            return new PenggunaResponse().GenerateResultFromLoginStatus(loginResult);
        }
        catch(SQLException exception)
        {
            ShowSqlErrorMessage(exception);
            return null;
        }
    }
    
    public PenggunaResponse Register()
    {
        try
        {
            Map<Pengguna, Object> validationResult = _registerValidation.ValidateForm();
            if(validationResult.isEmpty()) return null;
            Repository.Create(validationResult);
            return new PenggunaResponse().GenerateResultFromValidation(validationResult);
        }
        catch(SQLException exception)
        {
            ShowSqlErrorMessage(exception);
            return null;
        }
    }
    
}
