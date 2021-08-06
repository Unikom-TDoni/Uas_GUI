/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.kemahasiswaan.repository;

import java.util.Map;
import java.sql.ResultSet;
import java.sql.SQLException;
import edu.kemahasiswaan.table.MataKuliah;
import edu.kemahasiswaan.connection.DatabaseConnection;


/**
 *
 * @author Theod
 */
public final class MataKuliahRepository extends Repository<MataKuliah>
    implements IResourceRepository<MataKuliah>
{   
    public MataKuliahRepository()
    {
        super(MataKuliah.class);
    }
    
    @Override
    public ResultSet Create(Map<MataKuliah, Object> validData) throws SQLException
    {
        String query = String.format("insert into %s values (?, ?)", TableName);
        try (java.sql.PreparedStatement statement = DatabaseConnection.GetInstance().GetConnection().prepareStatement(query)) {
            statement.setObject(1, validData.get(MataKuliah.No));
            statement.setObject(2, validData.get(MataKuliah.Nama));
            statement.executeUpdate();
        }
        return null;
    }
    
    @Override
    public ResultSet SelectAll() throws SQLException
    {
        String query = String.format("select * from %s", TableName);
        return DatabaseConnection.GetInstance().GetConnection().prepareStatement(query).executeQuery();
    }
    
    @Override
    public void Update(Map<MataKuliah, Object> validData) throws SQLException
    {
        String query = String.format("update %s set %s = ? where %s = ?", TableName, MataKuliah.Nama.toString(), MataKuliah.No.toString());
        try (java.sql.PreparedStatement statement = DatabaseConnection.GetInstance().GetConnection().prepareStatement(query)) {
            statement.setObject(1, validData.get(MataKuliah.Nama));
            statement.setObject(2, validData.get(MataKuliah.No));
            statement.executeUpdate();
        }
    }
    
    @Override
    public void Delete(Map.Entry<MataKuliah, Object> validData) throws SQLException
    {
        String query = String.format("delete from %s where %s = ?", TableName, validData.getKey().toString());
        try (java.sql.PreparedStatement statement = DatabaseConnection.GetInstance().GetConnection().prepareStatement(query)) {
            statement.setObject(1, validData.getValue());
            statement.executeUpdate();
        }
    }
}
