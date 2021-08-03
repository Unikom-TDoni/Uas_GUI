/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.kemahasiswaan.controller;

import java.sql.SQLException;
import javax.swing.JOptionPane;
import edu.kemahasiswaan.response.MataKuliahResponse;
import edu.kemahasiswaan.repository.MataKuliahRepository;
import edu.kemahasiswaan.validation.MataKuliahValidation;

/**
 *
 * @author Theod
 */
public final class MataKuliahController extends Controller<MataKuliahValidation, MataKuliahRepository, MataKuliahResponse>
{
    public MataKuliahController(MataKuliahValidation validation) 
    {
        super(validation);
        Repository = new MataKuliahRepository();
    }

    @Override
    public MataKuliahResponse Create() 
    {
        try
        {
            var validationResult = Validation.ValidateCreateUpdateData();
            if(validationResult.isEmpty()) return null;
            Repository.Create(validationResult);
            return new MataKuliahResponse(validationResult);
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
    public MataKuliahResponse SelectAll() 
    {
        try
        {
            return new MataKuliahResponse(Repository.SelectAll());
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
    public MataKuliahResponse Update() 
    {
        try
        {
            var validationResult = Validation.ValidateCreateUpdateData();
            if(validationResult.isEmpty()) return null;
            Repository.Update(validationResult);
            return new MataKuliahResponse(validationResult);
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
    public MataKuliahResponse Delete() 
    {
        try
        {
            var validationResult = Validation.ValidateDeleteData();
            if(validationResult == null) return null;
            Repository.Delete(validationResult);
            return new MataKuliahResponse(validationResult.getValue());
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
