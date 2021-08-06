/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.kemahasiswaan.controller;

import java.util.Map;
import java.util.HashMap;
import java.sql.SQLException;
import edu.kemahasiswaan.table.SimulasiNilai;
import edu.kemahasiswaan.response.SimulasiNilaiResponse;
import edu.kemahasiswaan.repository.SimulasiNilaiRepository;
import edu.kemahasiswaan.validation.SimulasiNilaiValidation;

/**
 *
 * @author Theod
 */
public final class SimulasiNilaiController extends Controller<SimulasiNilaiRepository>
        implements IResourceController<SimulasiNilaiResponse>
{
    private final SimulasiNilaiValidation _validation;
    
    public SimulasiNilaiController(SimulasiNilaiValidation validation) 
    {
        _validation = validation;
        Repository = new SimulasiNilaiRepository();
    }
    
    @Override
    public SimulasiNilaiResponse Create() 
    {
        try 
        {
            Map<SimulasiNilai, Object> validationResult = _validation.ValidateForm();
            if(validationResult.isEmpty()) return null;
            var queryResult = Repository.Create(validationResult);
            return new SimulasiNilaiResponse().GenerateResultFromQueryCreate(queryResult, validationResult);
        } 
        catch (SQLException execption) 
        {
            ShowSqlErrorMessage(execption);
            return null;
        }
    }

    @Override
    public SimulasiNilaiResponse SelectAll() 
    {
        try
        {
            return new SimulasiNilaiResponse().GenerateResultFromQuerySelect(Repository.SelectAll());
        }
        catch(SQLException exception)
        {
            ShowSqlErrorMessage(exception);
            return null;
        }
    }

    @Override
    public SimulasiNilaiResponse Update() 
    {
        try
        {
            Map<SimulasiNilai, Object> validationResult = _validation.ValidateForm();
            if(validationResult.isEmpty()) return null;
            Repository.Update(validationResult);
            return new SimulasiNilaiResponse().GenerateResultFromQueryUpdate(validationResult);
        }
        catch(SQLException exception)
        {
            ShowSqlErrorMessage(exception);
            return null;
        }
    }

    @Override
    public SimulasiNilaiResponse Delete() 
    {
        try
        {
            Map.Entry<SimulasiNilai, Object> validationResult = _validation.ValidateTable();
            if(validationResult.getKey() == null) return null;
            Repository.Delete(validationResult);
            HashMap<SimulasiNilai, Object> validationResponseResult = new HashMap<>()
            {{
                put(validationResult.getKey(), validationResult.getValue());
            }};
            return new SimulasiNilaiResponse().GenerateResultFromQueryDelete(validationResponseResult);
        }
        catch(SQLException exception)
        {
            ShowSqlErrorMessage(exception);
            return null;
        }
    }
}
