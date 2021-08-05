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
import edu.kemahasiswaan.table.MataKuliah;

/**
 *
 * @author Theod
 */
public class MataKuliahResponse extends Response<MataKuliah>
{
    public MataKuliahResponse GenerateResultFromQuery(ResultSet queryResult)
    {
        try
        {
            while (queryResult.next())
            {
                HashMap<MataKuliah, Object> rowData = new HashMap<>()
                {{
                    put(MataKuliah.No, queryResult.getString(MataKuliah.No.toString()));
                    put(MataKuliah.Nama, queryResult.getString(MataKuliah.Nama.toString()));
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
    
    public MataKuliahResponse GenerateResultFromValidation(Map<MataKuliah, Object> validationResult)
    {
        Result.add(validationResult);
        return this;
    }
}
