/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.kemahasiswaan.repository;

import java.util.Map;
import java.sql.ResultSet;
import java.sql.SQLException;
import com.mysql.jdbc.Statement;
import edu.kemahasiswaan.helper.DateFormatHelper;
import edu.kemahasiswaan.database.table.Mahasiswa;
import edu.kemahasiswaan.connection.DatabaseConnection;

/**
 *
 * @author Theod
 */
public final class MahasiswaRepository extends Repository<Mahasiswa>
{
    public MahasiswaRepository()
    {
        super(Mahasiswa.class);
    }
    
    @Override
    public void Create(Map<Mahasiswa, Object> validData) throws SQLException
    {
        var query = String.format("insert into %s values (?, ?, ?, ?, ?)", TableName);
        var statement = DatabaseConnection.GetInstance().GetConnection().prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
        statement.setString(1, validData.get(Mahasiswa.Nim).toString());
        statement.setString(2, validData.get(Mahasiswa.Nama).toString());
        statement.setString(3, validData.get(Mahasiswa.TempatLahir).toString());
        statement.setDate(4, DateFormatHelper.GetSqlFormatedDate(validData.get(Mahasiswa.TanggalLahir).toString()));
        statement.setString(5, validData.get(Mahasiswa.Alamat).toString());
        statement.executeUpdate();
    }
    
    @Override
    public ResultSet SelectAll() throws SQLException
    {
        var query = String.format("select * from %s", TableName);
        return DatabaseConnection.GetInstance().GetConnection().prepareStatement(query).executeQuery(query);
    }
    
    @Override
    public void Update(Map<Mahasiswa, Object> validData) throws SQLException
    {
        var query = String.format("update %s set %s = ?, %s = ?, %s = ?, %s = ? where %s = ?",
            TableName, Mahasiswa.Nama.toString(), Mahasiswa.TempatLahir.toString(),
            Mahasiswa.TanggalLahir.toString(), Mahasiswa.Alamat.toString(), Mahasiswa.Nim.toString()
        );
        var statement = DatabaseConnection.GetInstance().GetConnection().prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
        statement.setString(1, validData.get(Mahasiswa.Nama).toString());
        statement.setString(2, validData.get(Mahasiswa.TempatLahir).toString());
        statement.setDate(3, DateFormatHelper.GetSqlFormatedDate(validData.get(Mahasiswa.TanggalLahir).toString()));
        statement.setString(4, validData.get(Mahasiswa.Alamat).toString());
        statement.setString(5, validData.get(Mahasiswa.Nim).toString());
        statement.executeUpdate();
    }
    
    @Override
    public void Delete(Map.Entry<Mahasiswa, Object> validData) throws SQLException
    {
        var query = String.format("delete from %s where %s = ?", TableName, validData.getKey().toString());
        var statement = DatabaseConnection.GetInstance().GetConnection().prepareStatement(query);
        statement.setString(1, validData.getValue().toString());
        statement.executeUpdate();
    }
}
