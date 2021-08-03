/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.kemahasiswaan.response;

import java.util.Map;
import java.util.HashMap;
import java.util.HashSet;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import edu.kemahasiswaan.database.table.Mahasiswa;


/**
 *
 * @author Theod
 */
public final class MahasiswaResponse extends Response<Mahasiswa>
{
    public MahasiswaResponse(ResultSet selectOperationResult)
    {
        super(selectOperationResult);
    } 
    
    public MahasiswaResponse(Map<Mahasiswa, Object> createUpdateOperationResult)
    {
        super(createUpdateOperationResult);
    }
    
    public MahasiswaResponse(Object deleteOperationResult)
    {
        super(deleteOperationResult);
    } 
    
    @Override
    public HashSet<Map<Mahasiswa, Object>> GetSelectOperationResult()
    {
        var result = new HashSet<Map<Mahasiswa, Object>>();
        try
        {
            while (SelectOperationResult.next())
            {
                var rowData = new HashMap<Mahasiswa, Object>()
                {{
                    put(Mahasiswa.Nim, SelectOperationResult.getString(Mahasiswa.Nim.toString()));
                    put(Mahasiswa.Nama, SelectOperationResult.getString(Mahasiswa.Nama.toString()));
                    put(Mahasiswa.Alamat, SelectOperationResult.getString(Mahasiswa.Alamat.toString()));
                    put(Mahasiswa.TempatLahir, SelectOperationResult.getString(Mahasiswa.TempatLahir.toString()));
                    put(Mahasiswa.TanggalLahir, SelectOperationResult.getDate(Mahasiswa.TanggalLahir.toString()));
                }};
                result.add(rowData);
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
            try { SelectOperationResult.close(); } catch (SQLException exception) { }
        }
        return result;
    }
}
