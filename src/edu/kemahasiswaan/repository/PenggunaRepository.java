/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.kemahasiswaan.repository;

import java.util.Map;
import java.sql.SQLException;
import edu.kemahasiswaan.table.Pengguna;
import edu.kemahasiswaan.helper.HashFormatHelper;
import edu.kemahasiswaan.connection.DatabaseConnection;

/**
 *
 * @author Theod
 */
public final class PenggunaRepository extends Repository<Pengguna>
{
    public PenggunaRepository() 
    {
        super(Pengguna.class);
    }

    public void Create(Map<Pengguna, Object> validData) throws SQLException 
    {
        String query = String.format("insert into %s values (?, ?)", TableName);
        try (java.sql.PreparedStatement statement = DatabaseConnection.GetInstance().GetConnection().prepareStatement(query)) {
            statement.setString(1, validData.get(Pengguna.Username).toString());
            statement.setString(2, HashFormatHelper.HashSHA512(validData.get(Pengguna.Password).toString()));
            statement.executeUpdate();
        }
    }
    
    public boolean IsLoginSuccess(Map<Pengguna, Object> validData) throws SQLException
    {
        String query = String.format("select * from %s where %s = ? and %s = ?", 
                TableName, Pengguna.Username.toString(), Pengguna.Password.toString());
        try (java.sql.PreparedStatement statement = DatabaseConnection.GetInstance().GetConnection().prepareStatement(query)) {
            statement.setString(1, validData.get(Pengguna.Username).toString());
            statement.setString(2, HashFormatHelper.HashSHA512(validData.get(Pengguna.Password).toString()));
            return statement.executeQuery().next();
        }
    }
}
