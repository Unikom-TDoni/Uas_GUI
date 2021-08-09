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
import edu.kemahasiswaan.table.NilaiMahasiswa;
import edu.kemahasiswaan.connection.DatabaseConnection;


/**
 *
 * @author Theod
 */
public class NilaiMahasiswaRepository extends Repository<NilaiMahasiswa>
    implements IResourceRepository<NilaiMahasiswa>
{

    public NilaiMahasiswaRepository() 
    {
        super(NilaiMahasiswa.class);
    }

    @Override
    public ResultSet Create(Map<NilaiMahasiswa, Object> validData) throws SQLException 
    {
        String query = String.format("insert into %s(%s,%s,%s,%s,%s,%s,%s,%s,%s) values (?, ?, ?, ?, ?, ?, ?, ?, ?)", TableName,
               NilaiMahasiswa.Nim.toString(), 
               NilaiMahasiswa.NoMk.toString(),
               NilaiMahasiswa.Kehadiran.toString(),
               NilaiMahasiswa.TugasPertama.toString(),
               NilaiMahasiswa.TugasKedua.toString(),
               NilaiMahasiswa.TugasKetiga.toString(),
               NilaiMahasiswa.Uts.toString(),
               NilaiMahasiswa.Uas.toString(),
               NilaiMahasiswa.Angkatan.toString()
        );
        
        java.sql.PreparedStatement statement = DatabaseConnection.GetInstance().GetConnection().prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
        statement.setObject(1, validData.get(NilaiMahasiswa.Nim));
        statement.setObject(2, validData.get(NilaiMahasiswa.NoMk));
        statement.setObject(3, validData.get(NilaiMahasiswa.Kehadiran));
        statement.setObject(4, validData.get(NilaiMahasiswa.TugasPertama));
        statement.setObject(5, validData.get(NilaiMahasiswa.TugasKedua));
        statement.setObject(6, validData.get(NilaiMahasiswa.TugasKetiga));
        statement.setObject(7, validData.get(NilaiMahasiswa.Uts));
        statement.setObject(8, validData.get(NilaiMahasiswa.Uas));
        statement.setObject(9, validData.get(NilaiMahasiswa.Angkatan));
        statement.executeUpdate();
        return statement.getGeneratedKeys();
    }

    @Override
    public ResultSet SelectAll() throws SQLException  
    {
        String query = String.format("select %s.*, mata_kuliah.nama as nama_mk, mahasiswa.nama as nama_mhs from %s natural join mahasiswa inner join mata_kuliah on mata_kuliah.no = %s.no_mk ", 
                TableName, TableName, TableName);
        return DatabaseConnection.GetInstance().GetConnection().prepareStatement(query).executeQuery();
    }

    @Override
    public void Update(Map<NilaiMahasiswa, Object> validData) throws SQLException 
    {
       String query = String.format("update %s set %s = ?, %s = ?, %s = ?, %s = ?, %s = ?, %s = ?, %s = ?, %s = ?, %s = ? where %s = ?", TableName, 
               NilaiMahasiswa.Nim.toString(), 
               NilaiMahasiswa.NoMk.toString(),
               NilaiMahasiswa.Kehadiran.toString(),
               NilaiMahasiswa.TugasPertama.toString(),
               NilaiMahasiswa.TugasKedua.toString(),
               NilaiMahasiswa.TugasKetiga.toString(),
               NilaiMahasiswa.Uts.toString(),
               NilaiMahasiswa.Uas.toString(),
               NilaiMahasiswa.Angkatan.toString(),
               NilaiMahasiswa.No.toString()
               );
       
        try (java.sql.PreparedStatement statement = DatabaseConnection.GetInstance().GetConnection().prepareStatement(query)) {
            statement.setObject(1, validData.get(NilaiMahasiswa.Nim));
            statement.setObject(2, validData.get(NilaiMahasiswa.NoMk));
            statement.setObject(3, validData.get(NilaiMahasiswa.Kehadiran));
            statement.setObject(4, validData.get(NilaiMahasiswa.TugasPertama));
            statement.setObject(5, validData.get(NilaiMahasiswa.TugasKedua));
            statement.setObject(6, validData.get(NilaiMahasiswa.TugasKetiga));
            statement.setObject(7, validData.get(NilaiMahasiswa.Uts));
            statement.setObject(8, validData.get(NilaiMahasiswa.Uas));
            statement.setObject(9, validData.get(NilaiMahasiswa.Angkatan));
        }
    }

    @Override
    public void Delete(Map.Entry<NilaiMahasiswa, Object> validData) throws SQLException {
        String query = String.format("delete from %s where %s = ?", TableName, validData.getKey().toString());
        try (java.sql.PreparedStatement statement = DatabaseConnection.GetInstance().GetConnection().prepareStatement(query)) {
            statement.setString(1, validData.getValue().toString());
            statement.executeUpdate();
        }
    }
    
}
