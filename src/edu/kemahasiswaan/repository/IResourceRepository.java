/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.kemahasiswaan.repository;

import java.util.Map;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Theod
 * @param <T>
 */
public interface IResourceRepository<T extends Enum>
{
    public void Create(Map<T, Object> validData) throws SQLException;
    
    public ResultSet SelectAll() throws SQLException;
    
    public void Update(Map<T, Object> validData) throws SQLException;
    
    public void Delete(Map.Entry<T, Object> validData) throws SQLException;
}
