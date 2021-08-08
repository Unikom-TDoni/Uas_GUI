/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.kemahasiswaan.response;

import java.util.Map;
import java.util.HashMap;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import edu.kemahasiswaan.table.Transaksi;
import java.util.LinkedList;

/**
 *
 * @author Theod
 */
public final class TransaksiResponse extends Response<Transaksi> 
{
    public TransaksiResponse GenerateResultFromQuerySelect(ResultSet queryResult)
    {
        try
        {
            while (queryResult.next())
            {
                HashMap<Transaksi, Object> rowData = new HashMap<>()
                {{
                    put(Transaksi.id, queryResult.getInt(Transaksi.id.toString()));
                    put(Transaksi.NomorTelp, queryResult.getString(Transaksi.NomorTelp.toString()));
                    put(Transaksi.Operator, queryResult.getString(Transaksi.Operator.toString()));
                    put(Transaksi.Nominal, queryResult.getInt(Transaksi.Nominal.toString()));
                    put(Transaksi.MetodePembayaran, queryResult.getString(Transaksi.MetodePembayaran.toString()));
                    put(Transaksi.Tanggal, queryResult.getDate(Transaksi.Tanggal.toString()));
                    put(Transaksi.Status, queryResult.getString(Transaksi.Status.toString()));
                }};
                Result.add(rowData);
            }
        }
        catch(SQLException exception)
        {
            JOptionPane.showMessageDialog(null, 
                exception.getMessage(), "Error", 
                JOptionPane.INFORMATION_MESSAGE
            );
        }
        finally
        {
            try { queryResult.close(); } catch (SQLException exception) { }
        }
        return this;
    }
    
    public TransaksiResponse GenerateResultQuerySelectOperator(ResultSet nominalOperatorResult)
    {
        try
        {
            HashMap<String, LinkedList<Integer>> result = new HashMap<>();
            LinkedList<Integer> nominal = new LinkedList<>();
            while(nominalOperatorResult.next())
            {
                nominal.add(nominalOperatorResult.getInt("nominal"));
                String namaOp = nominalOperatorResult.getString("nama");
                if(!result.containsKey(namaOp))
                    nominal.clear();
                result.put(namaOp, nominal);
            }
            
            Result.add(new HashMap<>()
            {{ 
                put(Transaksi.Operator, result); 
            }});
        }
        catch(SQLException exception)
        {
            JOptionPane.showMessageDialog(null, 
                exception.getMessage(), "Error", 
                JOptionPane.INFORMATION_MESSAGE
            );
        }
        finally
        {
            try { nominalOperatorResult.close(); } catch (SQLException exception) { }
        }
        return this;
    }
    
    public TransaksiResponse GenerateResultFromQuerySelectMetodePembayaran(ResultSet metodePembayaran)
    {
        try
        {
            LinkedList<String> result = new LinkedList<>();
            while(metodePembayaran.next()) result.add(metodePembayaran.getString("nama"));
            Result.add(new HashMap<>()
            {{ 
                put(Transaksi.MetodePembayaran, result); 
            }});
        }
        catch(SQLException exception)
        {
            JOptionPane.showMessageDialog(null, 
                exception.getMessage(), "Error", 
                JOptionPane.INFORMATION_MESSAGE
            );
        }
        finally
        {
            try { metodePembayaran.close(); } catch (SQLException exception) { }
        }
        return this;
    }
    
    public TransaksiResponse GenerateResultFromQueryCreate(ResultSet queryResult, Map<Transaksi, Object> validationResult)
    {
        try
        {
            queryResult.next();
            validationResult.put(Transaksi.id, queryResult.getLong(1));
            Result.add(validationResult);
        }
        catch(SQLException exception)
        {
            JOptionPane.showMessageDialog(null, 
                exception.getMessage(), "Error", 
                JOptionPane.INFORMATION_MESSAGE
            );
        }
        finally
        {
            try { queryResult.close(); } catch (SQLException exception) { }
        }
        return this;
    }
    
    public TransaksiResponse GenerateResultFromValidation(Map<Transaksi, Object> validationResult)
    {
        Result.add(validationResult);
        return this;
    }
}
