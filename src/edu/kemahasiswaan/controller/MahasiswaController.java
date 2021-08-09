/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.kemahasiswaan.controller;

import java.util.Map;
import java.util.HashMap;
import java.sql.SQLException;
import edu.kemahasiswaan.table.Mahasiswa;
import edu.kemahasiswaan.response.MahasiswaResponse;
import edu.kemahasiswaan.repository.MahasiswaRepository;
import edu.kemahasiswaan.validation.MahasiswaValidation;
import javax.swing.JOptionPane;

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
            Map<Mahasiswa, Object> validationResult = _validation.ValidateForm();
            if(validationResult.isEmpty()) return null;
            Repository.Create(validationResult);
            return new MahasiswaResponse().GenerateResultFromValidation(validationResult);
        }
        catch(SQLException exception)
        {
            ShowSqlErrorMessage(exception);
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
            ShowSqlErrorMessage(exception);
            return null;
        }
    }
    
    @Override
    public MahasiswaResponse Update() 
    {
        try
        {
            Map<Mahasiswa, Object> validationResult = _validation.ValidateForm();
            if(validationResult.isEmpty()) return null;
            Repository.Update(validationResult);
            return new MahasiswaResponse().GenerateResultFromValidation(validationResult);
        }
        catch(SQLException exception)
        {
            ShowSqlErrorMessage(exception);
            return null;
        }
    }
    
    @Override
    public MahasiswaResponse Delete() 
    {
        try
        {
            Map.Entry<Mahasiswa, Object> validationResult = _validation.ValidateTable();
            if(validationResult.getKey() == null) return null;
            int CancelDialog = JOptionPane.showConfirmDialog(null, "Apakah anda yakin untuk mengahapus data mahasiswa yang anda pilih?", "Warning", JOptionPane.YES_NO_OPTION);
            if(CancelDialog == JOptionPane.NO_OPTION) return null;
            Repository.Delete(validationResult);
            HashMap<Mahasiswa, Object> responseResult = new HashMap<>()
            {{
                put(validationResult.getKey(), validationResult.getValue());
            }};
            return new MahasiswaResponse().GenerateResultFromValidation(responseResult);
        }
        catch(SQLException exception)
        {
            ShowSqlErrorMessage(exception);
            return null;
        }
    }
    
    public MahasiswaResponse SelectNimNamaFields()
    {
        try
        {
            return new MahasiswaResponse().GenerateResultFromQuery(Repository.SelectAll());
        }
        catch(SQLException exception)
        {
            ShowSqlErrorMessage(exception);
            return null;
        }
    }
}
