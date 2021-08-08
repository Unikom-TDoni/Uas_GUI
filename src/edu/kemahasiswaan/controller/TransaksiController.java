/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.kemahasiswaan.controller;

import java.util.Map;
import java.util.HashMap;
import java.sql.SQLException;
import edu.kemahasiswaan.table.Transaksi;
import edu.kemahasiswaan.response.TransaksiResponse;
import edu.kemahasiswaan.repository.TransaksiRepository;
import edu.kemahasiswaan.validation.TransaksiValidation;

/**
 *
 * @author Theod
 */
public final class TransaksiController extends Controller<TransaksiRepository>
        implements IResourceController<TransaksiResponse>
{
    private final TransaksiValidation _validation;
    
    public TransaksiController(TransaksiValidation validation) 
    {
        _validation = validation;
        Repository = new TransaksiRepository();
    }
    
    
    @Override
    public TransaksiResponse Create() 
    {
        try
        {
            Map<Transaksi, Object> validationResult = _validation.validateFormCreate();
            if(validationResult.isEmpty()) return null;
            return new TransaksiResponse().GenerateResultFromQueryCreate(Repository.Create(validationResult), validationResult);
        }
        catch(SQLException exception)
        {
            ShowSqlErrorMessage(exception);
            return null;
        }
    }

    @Override
    public TransaksiResponse SelectAll() 
    {
        try
        {
            return new TransaksiResponse().GenerateResultFromQuerySelect(Repository.SelectAll());
        }
        catch(SQLException exception)
        {
            ShowSqlErrorMessage(exception);
            return null;
        }
    }

    @Override
    public TransaksiResponse Update() 
    {
        try
        {
            Map<Transaksi, Object> validationResult = _validation.ValidateForm();
            if(validationResult.isEmpty()) return null;
            Repository.Update(validationResult);
            return new TransaksiResponse().GenerateResultFromValidation(validationResult);
        }
        catch(SQLException exception)
        {
            ShowSqlErrorMessage(exception);
            return null;
        }
    }

    @Override
    public TransaksiResponse Delete() 
    {
        try
        {
            Map.Entry<Transaksi, Object> validationResult = _validation.ValidateTable();
            if(validationResult.getKey() == null) return null;
            Repository.Delete(validationResult);
            HashMap<Transaksi, Object> validationResponseResult = new HashMap<>()
            {{
                put(validationResult.getKey(), validationResult.getValue());
            }};
            return new TransaksiResponse().GenerateResultFromValidation(validationResponseResult);
        }
        catch(SQLException exception)
        {
            ShowSqlErrorMessage(exception);
            return null;
        }
    }
    
    public TransaksiResponse SelectAllOperatorNominal(){
        try
        {
            return new TransaksiResponse().GenerateResultQuerySelectOperator(Repository.SelectAllOperatorNominal());
        }
        catch(SQLException exception)
        {
            ShowSqlErrorMessage(exception);
            return null;
        }
    }
    
    public TransaksiResponse SelectAllMetodePembayaran()
    {
        try
        {
            return new TransaksiResponse().GenerateResultFromQuerySelectMetodePembayaran(Repository.SelectAllMetodePembayaran());
        }
        catch(SQLException exception)
        {
            ShowSqlErrorMessage(exception);
            return null;
        }
    }
}
