/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.kemahasiswaan.controller;

import java.sql.SQLException;
import javax.swing.JOptionPane;
import edu.kemahasiswaan.response.MahasiswaResponse;
import edu.kemahasiswaan.repository.MahasiswaRepository;
import edu.kemahasiswaan.validation.MahasiswaValidation;

/**
 *
 * @author Theod
 */
public final class MahasiswaController extends Controller<MahasiswaValidation, MahasiswaRepository, MahasiswaResponse>
{
    public MahasiswaController(MahasiswaValidation validation)
    {
        super(validation);
        Repository = new MahasiswaRepository();
    }
    
    @Override
    public MahasiswaResponse Create() 
    {
        try
        {
            var validationResult = Validation.ValidateCreateUpdateData();
            if(validationResult.isEmpty()) return null;
            Repository.Create(validationResult);
            return new MahasiswaResponse(validationResult);
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
            return new MahasiswaResponse(Repository.SelectAll());
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
            var validationResult = Validation.ValidateCreateUpdateData();
            if(validationResult.isEmpty()) return null;
            Repository.Update(validationResult);
            return new MahasiswaResponse(validationResult);
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
            var validationResult = Validation.ValidateDeleteData();
            if(validationResult == null) return null;
            Repository.Delete(validationResult);
            return new MahasiswaResponse(validationResult.getValue());
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
