/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.kemahasiswaan.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import edu.kemahasiswaan.connection.DatabaseConnection;
import edu.kemahasiswaan.model.MataKuliahModel;

/**
 *
 * @author Theod
 */
public final class MataKuliahRepository extends KemahasiswaanRepository<MataKuliahModel>
{
    @Override
    public void Create(MataKuliahModel model) throws SQLException
    {
        var query = String.format("insert into mata_kuliah values = (%s, %s)", 
                model.GetNo(), model.GetNama());
        DatabaseConnection.GetInstance().GetStatement().executeQuery(query);
    }
    
    @Override
    public ResultSet GetAll() throws SQLException
    {
        var query = "select * from mata_kuliah";
        return DatabaseConnection.GetInstance().GetStatement().executeQuery(query);
    }
    
    @Override
    public ResultSet Find(String key) throws SQLException
    {
        var query = String.format("select * from mata_kuliah where no = %s", key);
        return DatabaseConnection.GetInstance().GetStatement().executeQuery(query);
    }
    
    @Override
    public void Update(MataKuliahModel model)
    {
        var query = String.format("update mata_kuliah set nama = %s where no = %s", 
            model.GetNama(), model.GetNo());
    }
    
    @Override
    public void Delete(String key) throws SQLException
    {
        var query = String.format("delete from mata_kuliah where no = %s", key);
        DatabaseConnection.GetInstance().GetStatement().executeQuery(query);
    }
}
