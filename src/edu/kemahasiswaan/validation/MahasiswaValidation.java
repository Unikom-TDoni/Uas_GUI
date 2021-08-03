/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.kemahasiswaan.validation;

import java.util.Map;
import java.util.Collections;
import java.util.AbstractMap;
import edu.kemahasiswaan.handler.*;
import edu.kemahasiswaan.database.table.Mahasiswa;

/**
 *
 * @author Theod
 */
public final class MahasiswaValidation extends Validation<Mahasiswa>
{
    public MahasiswaValidation(JTableHandler tableHandler, JTextFieldHandler textFieldHandler) 
    {
        super(tableHandler, textFieldHandler);
    }

    @Override
    public Map<Mahasiswa, Object> ValidateCreateUpdateData() 
    {
        if(IsHaveNullField())
            return Collections.emptyMap();
        return GetValidFieldData();
    }

    @Override
    public Map.Entry<Mahasiswa, Object> ValidateDeleteData() 
    {
        var rowIndex = TableHandler.GetSelectedRowIndex();
        if(!IsRowValid(rowIndex)) return new AbstractMap.SimpleEntry<>(null,null);
        return GetValidTableData(rowIndex);
    }
}
