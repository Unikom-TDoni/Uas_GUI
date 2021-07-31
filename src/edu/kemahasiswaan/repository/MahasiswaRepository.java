/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.kemahasiswaan.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import edu.kemahasiswaan.model.MahasiswaModel;
import edu.kemahasiswaan.connection.DatabaseConnection;

/**
 *
 * @author Theod
 */
public final class MahasiswaRepository extends KemahasiswaanRepository<MahasiswaModel>
{
    @Override
    public void Create(MahasiswaModel model) throws SQLException
    {
        var query = String.format("insert into mahasiswa values = (%s, %s, %s, %s, %s)", 
                model.GetNim(), model.GetNama(), model.GetTempatLahir(), model.GetTanggalLahir(), model.GetAlamat());
        DatabaseConnection.GetInstance().GetStatement().executeQuery(query);
    }
    
    @Override
    public ResultSet GetAll() throws SQLException
    {
        var query = "select * from mahasiswa";
        return DatabaseConnection.GetInstance().GetStatement().executeQuery(query);
    }
    
    @Override
    public ResultSet Find(String key) throws SQLException
    {
        var query = String.format("select * from mahasiswa where nim = %s", key);
        return DatabaseConnection.GetInstance().GetStatement().executeQuery(query);
    }
    
    @Override
    public void Update(MahasiswaModel model) throws SQLException
    {
        var query = String.format("update mahasiswa set nama = %s, tempat_lahir = %s, tanggal_lahir = %s, alamat = %s where nim = %s", 
            model.GetNama(), model.GetTempatLahir(), model.GetTanggalLahir(), model.GetAlamat(), model.GetNim());
        DatabaseConnection.GetInstance().GetStatement().executeQuery(query);
    }
    
    @Override
    public void Delete(String key) throws SQLException
    {
        var query = String.format("delete from mahasiswa where nim = %s", key);
        DatabaseConnection.GetInstance().GetStatement().executeQuery(query);
    }
}
