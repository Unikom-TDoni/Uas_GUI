/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.kemahasiswaan.controller;

import java.util.Map;
import java.util.HashMap;
import java.sql.SQLException;
import edu.kemahasiswaan.table.NilaiMahasiswa;
import edu.kemahasiswaan.response.NilaiMahasiswaResponse;
import edu.kemahasiswaan.repository.NilaiMahasiswaRepository;
import edu.kemahasiswaan.validation.NilaiMahasiswaValidation;

/**
 *
 * @author Theod
 */
public final class NilaiMahasiswaController extends Controller<NilaiMahasiswaRepository>
        implements IResourceController<NilaiMahasiswaResponse>
{
    private final NilaiMahasiswaValidation _validation;
    
    public NilaiMahasiswaController(NilaiMahasiswaValidation validation) 
    {
        _validation = validation;
        Repository = new NilaiMahasiswaRepository();
    }
    
    @Override
    public NilaiMahasiswaResponse Create() 
    {
        try
        {
            Map<NilaiMahasiswa, Object> validationResult = _validation.ValidateForm();
            if(validationResult.isEmpty()) return null;
            return new NilaiMahasiswaResponse().GenerateResultFromQueryCreate(Repository.Create(validationResult), validationResult);
        }
        catch(SQLException exception)
        {
            ShowSqlErrorMessage(exception);
            return null;
        }
    }

    @Override
    public NilaiMahasiswaResponse SelectAll() 
    {
        try
        {
            return new NilaiMahasiswaResponse().GenerateResultFromQuerySelect(Repository.SelectAll());
        }
        catch(SQLException exception)
        {
            ShowSqlErrorMessage(exception);
            return null;
        }
    }

    @Override
    public NilaiMahasiswaResponse Update() 
    {
        try
        {
            Map<NilaiMahasiswa, Object> validationResult = _validation.ValidateForm();
            if(validationResult.isEmpty()) return null;
            Repository.Update(validationResult);
            return new NilaiMahasiswaResponse().GenerateResultFromQueryUpdate(validationResult);
        }
        catch(SQLException exception)
        {
            ShowSqlErrorMessage(exception);
            return null;
        }
    }

    @Override
    public NilaiMahasiswaResponse Delete() 
    {
        try
        {
            Map.Entry<NilaiMahasiswa, Object> validationResult = _validation.ValidateTable();
            if(validationResult.getKey() == null) return null;
            Repository.Delete(validationResult);
            HashMap<NilaiMahasiswa, Object> validationResponseResult = new HashMap<>()
            {{
                put(validationResult.getKey(), validationResult.getValue());
            }};
            return new NilaiMahasiswaResponse().GenerateResultFromQueryDelete(validationResponseResult);
        }
        catch(SQLException exception)
        {
            ShowSqlErrorMessage(exception);
            return null;
        }
    }
}
