/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.kemahasiswaan.controller;

import java.util.HashMap;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import edu.kemahasiswaan.table.Mahasiswa;
import edu.kemahasiswaan.response.MahasiswaResponse;
import edu.kemahasiswaan.repository.MahasiswaRepository;
import edu.kemahasiswaan.validation.MahasiswaValidation;

/**
 *
 * @author Theod
 */
public final class MahasiswaController extends Controller<MahasiswaRepository>
        implements IResourceController<MahasiswaResponse>
{
    private final MahasiswaValidation _validation;
    
    public MahasiswaController(MahasiswaValidation validation)
    {
        _validation = validation;
        Repository = new MahasiswaRepository();
    }
    
    @Override
    public MahasiswaResponse Create() 
    {
        try
        {
            var validationResult = _validation.ValidateForm();
            if(validationResult.isEmpty()) return null;
            Repository.Create(validationResult);
            return new MahasiswaResponse().GenerateResultFromValidation(validationResult);
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
    
    @Override
    public MahasiswaResponse SelectAll() 
    {
        try
        {
            return new MahasiswaResponse().GenerateResultFromQuery(Repository.SelectAll());
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
    
    @Override
    public MahasiswaResponse Update() 
    {
        try
        {
            var validationResult = _validation.ValidateForm();
            if(validationResult.isEmpty()) return null;
            Repository.Update(validationResult);
            return new MahasiswaResponse().GenerateResultFromValidation(validationResult);
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
    
    @Override
    public MahasiswaResponse Delete() 
    {
        try
        {
            var validationResult = _validation.ValidateTable();
            if(validationResult == null) return null;
            Repository.Delete(validationResult);
            var responseResult = new HashMap<Mahasiswa, Object>()
            {{
                put(validationResult.getKey(), validationResult.getValue());
            }};
            return new MahasiswaResponse().GenerateResultFromValidation(responseResult);
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
