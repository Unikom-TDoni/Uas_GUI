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
    public void Create(Map<MataKuliah, Object> validData) throws SQLException
    {
        var query = String.format("insert into %s values (?, ?)", TableName);
        var statement = DatabaseConnection.GetInstance().GetConnection().prepareStatement(query);
        statement.setString(1, validData.get(MataKuliah.No).toString());
        statement.setString(2, validData.get(MataKuliah.Nama).toString());
        statement.executeUpdate();
        statement.close();
    }
    
    @Override
    public ResultSet SelectAll() throws SQLException
    {
        var query = String.format("select * from %s", TableName);
        return DatabaseConnection.GetInstance().GetConnection().prepareStatement(query).executeQuery(query);
    }
    
    @Override
    public void Update(Map<MataKuliah, Object> validData) throws SQLException
    {
        var query = String.format("update %s set %s = ? where %s = ?", TableName, MataKuliah.Nama.toString(), MataKuliah.No.toString());
        var statement = DatabaseConnection.GetInstance().GetConnection().prepareStatement(query);
        statement.setString(1, validData.get(MataKuliah.Nama).toString());
        statement.setString(2, validData.get(MataKuliah.No).toString());
        statement.executeUpdate();
        statement.close();
    }
    
    @Override
    public void Delete(Map.Entry<MataKuliah, Object> validData) throws SQLException
    {
        var query = String.format("delete from %s where %s = ?", TableName, validData.getKey().toString());
        var statement = DatabaseConnection.GetInstance().GetConnection().prepareStatement(query);
        statement.setString(1, validData.getValue().toString());
        statement.executeUpdate();
        statement.close();
    }
}
