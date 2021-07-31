/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.kemahasiswaan.repository;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Theod
 * @param <T>
 */
public abstract class KemahasiswaanRepository<T>
{
    public abstract void Create(T model) throws SQLException;
    
    public abstract ResultSet GetAll() throws SQLException;
    
    public abstract ResultSet Find(String key) throws SQLException;
    
    public abstract void Update(T model) throws SQLException;
    
    public abstract void Delete(String key) throws SQLException;
}
