/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.kemahasiswaan.repository;

import com.mysql.jdbc.Statement;
import java.util.Map;
import java.sql.ResultSet;
import java.sql.SQLException;
import edu.kemahasiswaan.table.Transaksi;
import edu.kemahasiswaan.connection.DatabaseConnection;

/**
 *
 * @author Theod
 */
public final class TransaksiRepository extends Repository<Transaksi>
    implements IResourceRepository<Transaksi>
{

    public TransaksiRepository() 
    {
        super(Transaksi.class);
    }

    @Override
    public ResultSet Create(Map<Transaksi, Object> validData) throws SQLException 
    {
        String query = String.format("insert into %s(id_operator, id_nominal_pulsa, id_metode_pembayaran, tanggal, status, nomor_telp) values("
                + "(select id from operator where nama=?),"
                + "(select id from nominal_pulsa where nominal=? and id_operator=(select id from operator where nama=?)),"
                + "(select id from metode_pembayaran where nama=?),"
                + "?, ?, ?)", TableName);
        
        java.sql.PreparedStatement statement = DatabaseConnection.GetInstance().GetConnection().prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
        statement.setObject(1, validData.get(Transaksi.Operator));
        statement.setObject(2, validData.get(Transaksi.Nominal));
        statement.setObject(3, validData.get(Transaksi.Operator));
        statement.setObject(4, validData.get(Transaksi.MetodePembayaran));
        statement.setObject(5, validData.get(Transaksi.Tanggal));
        statement.setObject(6, validData.get(Transaksi.Status));
        statement.setObject(7, validData.get(Transaksi.NomorTelp));
        statement.executeUpdate();
        return statement.getGeneratedKeys();
    }

    @Override
    public ResultSet SelectAll() throws SQLException 
    {
        String query = String.format("select %s.*, operator.nama as operator, nominal_pulsa.nominal as nominal, metode_pembayaran.nama as metode_pembayaran from %s "
                + "inner join operator on (operator.id = %s.id_operator)"
                + "inner join metode_pembayaran on (metode_pembayaran.id = %s.id_metode_pembayaran)"
                + "inner join nominal_pulsa on (nominal_pulsa.id = %s.id_nominal_pulsa)", 
                TableName, TableName, TableName, TableName, TableName);
        return DatabaseConnection.GetInstance().GetConnection().prepareStatement(query).executeQuery();
    }

    @Override
    public void Update(Map<Transaksi, Object> validData) throws SQLException 
    {
        String query = String.format("update %s set id_operator = (select id from operator where nama = ?),"
                + "id_nominal_pulsa = (select id from nominal_pulsa where nominal=? and id_operator=(select id from operator where nama = ?)),"
                + "id_metode_pembayaran = (select id from metode_pembayaran where nama=?),"
                + "status = ?, nomor_telp = ? where %s = ?", TableName, Transaksi.id.toString());
        try (java.sql.PreparedStatement statement = DatabaseConnection.GetInstance().GetConnection().prepareStatement(query)) 
        {
            statement.setObject(1, validData.get(Transaksi.Operator));
            statement.setObject(2, validData.get(Transaksi.Nominal));
            statement.setObject(3, validData.get(Transaksi.Operator));
            statement.setObject(4, validData.get(Transaksi.MetodePembayaran));
            statement.setObject(5, validData.get(Transaksi.Status));
            statement.setObject(6, validData.get(Transaksi.NomorTelp));
            statement.setObject(7, validData.get(Transaksi.id));
            statement.executeUpdate();
        }
    }

    @Override
    public void Delete(Map.Entry<Transaksi, Object> validData) throws SQLException 
    {
        String query = String.format("delete from %s where %s = ?", TableName, validData.getKey().toString());
        try (java.sql.PreparedStatement statement = DatabaseConnection.GetInstance().GetConnection().prepareStatement(query)) {
            statement.setString(1, validData.getValue().toString());
            statement.executeUpdate();
        }
    }
    
    public ResultSet SelectAllOperatorNominal() throws SQLException 
    {
        String query = "select operator.nama, nominal_pulsa.nominal from nominal_pulsa left join operator on nominal_pulsa.id_operator = operator.id";
        return DatabaseConnection.GetInstance().GetConnection().prepareStatement(query).executeQuery();
    }
    
    public ResultSet SelectAllMetodePembayaran() throws SQLException 
    {
        String query = "select nama from metode_pembayaran";
        return DatabaseConnection.GetInstance().GetConnection().prepareStatement(query).executeQuery();
    }
}