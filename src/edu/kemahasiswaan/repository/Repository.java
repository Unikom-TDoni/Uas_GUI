/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.kemahasiswaan.repository;

import java.util.Map;
import java.sql.ResultSet;
import java.sql.SQLException;
import edu.kemahasiswaan.helper.StringFormatHelper;

/**
 *
 * @author Theod
 * @param <T>
 */
public abstract class Repository<T extends Enum<T>>
{
    protected final String TableName;
    
    protected Repository(Class<T> enumClass)
    {
        TableName = StringFormatHelper.GetSplitedCamelCase(enumClass.getSimpleName(), "_").toLowerCase();
    }
    
    public abstract void Create(Map<T, Object> validData) throws SQLException;
    
    public abstract ResultSet SelectAll() throws SQLException;
    
    public abstract void Update(Map<T, Object> validData) throws SQLException;
    
    public abstract void Delete(Map.Entry<T, Object> validData) throws SQLException;
}
