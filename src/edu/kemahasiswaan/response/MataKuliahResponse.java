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
import edu.kemahasiswaan.database.table.MataKuliah;

/**
 *
 * @author Theod
 */
public class MataKuliahResponse extends Response<MataKuliah>
{
    public MataKuliahResponse(ResultSet selectOperationResult)
    {
        super(selectOperationResult);
    } 
    
    public MataKuliahResponse(Map<MataKuliah, Object> createUpdateOperationResult)
    {
        super(createUpdateOperationResult);
    }
    
    public MataKuliahResponse(Object deleteOperationResult)
    {
        super(deleteOperationResult);
    } 
    
    @Override
    public HashSet<Map<MataKuliah, Object>> GetSelectOperationResult()
    {
        var result = new HashSet<Map<MataKuliah, Object>>();
        try
        {
            while (SelectOperationResult.next())
            {
                var rowData = new HashMap<MataKuliah, Object>()
                {{
                    put(MataKuliah.No, SelectOperationResult.getString(MataKuliah.No.toString()));
                    put(MataKuliah.Nama, SelectOperationResult.getString(MataKuliah.Nama.toString()));
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
