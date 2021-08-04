/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.kemahasiswaan.controller;

import java.util.HashMap;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import edu.kemahasiswaan.table.MataKuliah;
import edu.kemahasiswaan.response.MataKuliahResponse;
import edu.kemahasiswaan.repository.MataKuliahRepository;
import edu.kemahasiswaan.validation.MataKuliahValidation;

/**
 *
 * @author Theod
 */
public final class MataKuliahController extends Controller<MataKuliahRepository>
        implements IResourceController<MataKuliahResponse>
{
    private final MataKuliahValidation _validation;
    
    public MataKuliahController(MataKuliahValidation validation) 
    {
        _validation = validation;
        Repository = new MataKuliahRepository();
    }

    @Override
    public MataKuliahResponse Create() 
    {
        try
        {
            var validationResult = _validation.ValidateForm();
            if(validationResult.isEmpty()) return null;
            Repository.Create(validationResult);
            return new MataKuliahResponse().GenerateResultFromValidation(validationResult);
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
            return new MataKuliahResponse().GenerateResultFromQuery(Repository.SelectAll());
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
            var validationResult = _validation.ValidateForm();
            if(validationResult.isEmpty()) return null;
            Repository.Update(validationResult);
            return new MataKuliahResponse().GenerateResultFromValidation(validationResult);
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
            var validationResult = _validation.ValidateTable();
            if(validationResult == null) return null;
            Repository.Delete(validationResult);
            var validationResponseResult = new HashMap<MataKuliah, Object>()
            {{
                put(validationResult.getKey(), validationResult.getValue());
            }};
            return new MataKuliahResponse().GenerateResultFromValidation(validationResponseResult);
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
