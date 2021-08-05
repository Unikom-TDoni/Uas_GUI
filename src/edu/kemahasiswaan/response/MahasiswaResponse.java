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
import edu.kemahasiswaan.table.Mahasiswa;
import edu.kemahasiswaan.helper.DateHelper;


/**
 *
 * @author Theod
 */
public final class MahasiswaResponse extends Response<Mahasiswa>
{
    public MahasiswaResponse GenerateResultFromQuery(ResultSet queryResult)
    {
        try
        {
            while (queryResult.next())
            {
                HashMap<Mahasiswa, Object> rowData = new HashMap<>()
                {{
                    put(Mahasiswa.Nim, queryResult.getString(Mahasiswa.Nim.toString()));
                    put(Mahasiswa.Nama, queryResult.getString(Mahasiswa.Nama.toString()));
                    put(Mahasiswa.Alamat, queryResult.getString(Mahasiswa.Alamat.toString()));
                    put(Mahasiswa.TempatLahir, queryResult.getString(Mahasiswa.TempatLahir.toString()));
                    put(Mahasiswa.TanggalLahir, DateHelper.GetFormatedDate(queryResult.getDate(Mahasiswa.TanggalLahir.toString())));
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
    
    public MahasiswaResponse GenerateResultFromValidation(Map<Mahasiswa, Object> validationResult)
    {
        Result.add(validationResult);
        return this;
    }
}
