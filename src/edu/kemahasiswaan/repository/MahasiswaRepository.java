/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.kemahasiswaan.repository;

import java.util.Map;
import java.sql.ResultSet;
import java.sql.SQLException;
import edu.kemahasiswaan.table.Mahasiswa;
import edu.kemahasiswaan.helper.DateHelper;
import edu.kemahasiswaan.connection.DatabaseConnection;

/**
 *
 * @author Theod
 */
public final class MahasiswaRepository extends Repository<Mahasiswa>
        implements IResourceRepository<Mahasiswa>
{
    public MahasiswaRepository()
    {
        super(Mahasiswa.class);
    }
    
    @Override
    public ResultSet Create(Map<Mahasiswa, Object> validData) throws SQLException
    {
        String query = String.format("insert into %s values (?, ?, ?, ?, ?)", TableName);
        try (java.sql.PreparedStatement statement = DatabaseConnection.GetInstance().GetConnection().prepareStatement(query)) {
            statement.setObject(1, validData.get(Mahasiswa.Nim));
            statement.setObject(2, validData.get(Mahasiswa.Nama));
            statement.setObject(3, validData.get(Mahasiswa.TempatLahir));
            statement.setDate(4, DateHelper.GetSqlFormatedDate(validData.get(Mahasiswa.TanggalLahir).toString()));
            statement.setObject(5, validData.get(Mahasiswa.Alamat));
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
    public void Update(Map<Mahasiswa, Object> validData) throws SQLException
    {
        String query = String.format("update %s set %s = ?, %s = ?, %s = ?, %s = ? where %s = ?",
            TableName, Mahasiswa.Nama.toString(), Mahasiswa.TempatLahir.toString(),
            Mahasiswa.TanggalLahir.toString(), Mahasiswa.Alamat.toString(), Mahasiswa.Nim.toString()
        );
        try (java.sql.PreparedStatement statement = DatabaseConnection.GetInstance().GetConnection().prepareStatement(query)) {
            statement.setObject(1, validData.get(Mahasiswa.Nama));
            statement.setObject(2, validData.get(Mahasiswa.TempatLahir));
            statement.setDate(3, DateHelper.GetSqlFormatedDate(validData.get(Mahasiswa.TanggalLahir).toString()));
            statement.setObject(4, validData.get(Mahasiswa.Alamat));
            statement.setObject(5, validData.get(Mahasiswa.Nim));
            statement.executeUpdate();
        }
    }
    
    @Override
    public void Delete(Map.Entry<Mahasiswa, Object> validData) throws SQLException
    {
        String query = String.format("delete from %s where %s = ?", TableName, validData.getKey().toString());
        try (java.sql.PreparedStatement statement = DatabaseConnection.GetInstance().GetConnection().prepareStatement(query)) {
            statement.setString(1, validData.getValue().toString());
            statement.executeUpdate();
        }
    }
    
    public ResultSet SelectNimNamaField() throws SQLException
    {
        String query = String.format("select nim, nama from %s", TableName);
        return DatabaseConnection.GetInstance().GetConnection().prepareStatement(query).executeQuery(query);
    }
}
