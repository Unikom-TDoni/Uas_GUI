/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.kemahasiswaan.response;

import java.util.Map;
import java.util.HashSet;
import java.sql.ResultSet;

/**
 *
 * @author Theod
 * @param <T>
 */
public abstract class Response<T extends Enum> 
{
    protected Object DeleteOperationResult;
    protected ResultSet SelectOperationResult;
    protected Map<T, Object> CreateUpdateOperationResult;
    
    public Response(ResultSet resultSet)
    {
        SelectOperationResult = resultSet;
    } 
    
    public Response(Map<T, Object> createUpdateResult)
    {
        CreateUpdateOperationResult = createUpdateResult;
    }
    
    public Response(Object deleteResult)
    {
        DeleteOperationResult = deleteResult;
    } 
    
    public Map<T, Object> GetCreateUpdateOperationResult()
    {
        return CreateUpdateOperationResult;
    }
    
    public Object GetDeleteOperationResult()
    {
        return DeleteOperationResult;
    }
    
    public abstract HashSet<Map<T, Object>> GetSelectOperationResult();
}
