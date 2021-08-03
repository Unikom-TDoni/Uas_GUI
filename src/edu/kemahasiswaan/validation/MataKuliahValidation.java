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
import edu.kemahasiswaan.database.table.MataKuliah;

/**
 *
 * @author Theod
 */
public final class MataKuliahValidation extends Validation<MataKuliah>
{
    public MataKuliahValidation(JTableHandler tableHandler, JTextFieldHandler textFieldHandler) 
    {
        super(tableHandler, textFieldHandler);
    }

    @Override
    public Map<MataKuliah, Object> ValidateCreateUpdateData() 
    {
        if(IsHaveNullField())
            return Collections.emptyMap();
        return GetValidFieldData();
    }

    @Override
    public Map.Entry<MataKuliah, Object> ValidateDeleteData() 
    {
        var rowIndex = TableHandler.GetSelectedRowIndex();
        if(!IsRowValid(rowIndex)) return new AbstractMap.SimpleEntry<>(null,null);
        return GetValidTableData(rowIndex);
    }
}
