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
import edu.kemahasiswaan.connection.DatabaseConnection;
import edu.kemahasiswaan.table.SimulasiNilai;

/**
 *
 * @author Theod
 */
public final class SimulasiNilaiRepository extends Repository<SimulasiNilai>
    implements IResourceRepository<SimulasiNilai>
{
    public SimulasiNilaiRepository() 
    {
        super(SimulasiNilai.class);
    }

    @Override
    public ResultSet Create(Map<SimulasiNilai, Object> validData) throws SQLException 
    {
        String query = String.format("insert into %s(%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)", TableName,
               SimulasiNilai.NoMk.toString(),
               SimulasiNilai.PresentaseKehadiran.toString(),
               SimulasiNilai.PresentaseTugas.toString(),
               SimulasiNilai.PresentaseUts.toString(),
               SimulasiNilai.PresentaseUas.toString(),
               SimulasiNilai.Kehadiran.toString(),
               SimulasiNilai.TugasPertama.toString(),
               SimulasiNilai.TugasKedua.toString(),
               SimulasiNilai.TugasKetiga.toString(),
               SimulasiNilai.Uts.toString(),
               SimulasiNilai.Uas.toString()
        );
        
        java.sql.PreparedStatement statement = DatabaseConnection.GetInstance().GetConnection().prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
        statement.setObject(1, validData.get(SimulasiNilai.NoMk));
        statement.setObject(2, validData.get(SimulasiNilai.PresentaseKehadiran));
        statement.setObject(3, validData.get(SimulasiNilai.PresentaseTugas));
        statement.setObject(4, validData.get(SimulasiNilai.PresentaseUts));
        statement.setObject(5, validData.get(SimulasiNilai.PresentaseUas));
        statement.setObject(6, validData.get(SimulasiNilai.Kehadiran));
        statement.setObject(7, validData.get(SimulasiNilai.TugasPertama));
        statement.setObject(8, validData.get(SimulasiNilai.TugasKedua));
        statement.setObject(9, validData.get(SimulasiNilai.TugasKetiga));
        statement.setObject(10, validData.get(SimulasiNilai.Uts));
        statement.setObject(11, validData.get(SimulasiNilai.Uas));
        statement.executeUpdate();
        return statement.getGeneratedKeys();
    }

    @Override
    public ResultSet SelectAll() throws SQLException 
    {
        String query = String.format("select %s.*, mata_kuliah.nama as nama_mk from %s inner join mata_kuliah on mata_kuliah.no = %s.no_mk ", 
                TableName, TableName, TableName);
        return DatabaseConnection.GetInstance().GetConnection().prepareStatement(query).executeQuery();
    }

    @Override
    public void Update(Map<SimulasiNilai, Object> validData) throws SQLException 
    {
        String query = String.format("update %s set %s = ?, %s = ?, %s = ?, %s = ?, %s = ?, %s = ?, %s = ?, %s = ?, %s = ?, %s = ?, %s = ? where %s = ?", TableName, 
               SimulasiNilai.NoMk.toString(),
               SimulasiNilai.PresentaseKehadiran.toString(),
               SimulasiNilai.PresentaseTugas.toString(),
               SimulasiNilai.PresentaseUts.toString(),
               SimulasiNilai.PresentaseUas.toString(),
               SimulasiNilai.Kehadiran.toString(),
               SimulasiNilai.TugasPertama.toString(),
               SimulasiNilai.TugasKedua.toString(),
               SimulasiNilai.TugasKetiga.toString(),
               SimulasiNilai.Uts.toString(),
               SimulasiNilai.Uas.toString(),
               SimulasiNilai.No.toString()
               );
       
        try (java.sql.PreparedStatement statement = DatabaseConnection.GetInstance().GetConnection().prepareStatement(query)) {
            statement.setObject(1, validData.get(SimulasiNilai.NoMk));
            statement.setObject(2, validData.get(SimulasiNilai.PresentaseKehadiran));
            statement.setObject(3, validData.get(SimulasiNilai.PresentaseTugas));
            statement.setObject(4, validData.get(SimulasiNilai.PresentaseUts));
            statement.setObject(5, validData.get(SimulasiNilai.PresentaseUas));
            statement.setObject(6, validData.get(SimulasiNilai.Kehadiran));
            statement.setObject(7, validData.get(SimulasiNilai.TugasPertama));
            statement.setObject(8, validData.get(SimulasiNilai.TugasKedua));
            statement.setObject(9, validData.get(SimulasiNilai.TugasKetiga));
            statement.setObject(10, validData.get(SimulasiNilai.Uts));
            statement.setObject(11, validData.get(SimulasiNilai.Uas));
            statement.setObject(12, validData.get(SimulasiNilai.No));
        }
    }

    @Override
    public void Delete(Map.Entry<SimulasiNilai, Object> validData) throws SQLException 
    {
        String query = String.format("delete from %s where %s = ?", TableName, validData.getKey().toString());
        try (java.sql.PreparedStatement statement = DatabaseConnection.GetInstance().GetConnection().prepareStatement(query)) {
            statement.setString(1, validData.getValue().toString());
            statement.executeUpdate();
        }
    }
    
}
