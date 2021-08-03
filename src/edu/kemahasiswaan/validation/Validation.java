/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.kemahasiswaan.validation;

import java.util.Map;
import java.util.AbstractMap;
import java.util.StringJoiner;
import javax.swing.JOptionPane;
import edu.kemahasiswaan.handler.*;
import java.util.stream.Collectors;
import edu.kemahasiswaan.helper.StringFormatHelper;

/**
 *
 * @author Theod
 * @param <T>
 */
public abstract class Validation<T extends Enum<T>> 
{
    protected final JTableHandler<T> TableHandler;
    protected final JTextFieldHandler<T> TextFieldHandler;
    
    public Validation(JTableHandler tableHandler, JTextFieldHandler textFieldHandler)
    {
        TableHandler = tableHandler;
        TextFieldHandler = textFieldHandler;
    }
    
    protected final boolean IsHaveNullField()
    {
        var stringJoiner = new StringJoiner(", ");
        var textFields = TextFieldHandler.GetTextFields();
        
        textFields.forEach((key, value) -> 
        {
            if(value.getText().equals(""))
                stringJoiner.add(StringFormatHelper.GetSplitedCamelCase(key.toString(), " "));
        });
        
        if(stringJoiner.length() == 0)
            return false;
        
        JOptionPane.showMessageDialog(null, "Field " + stringJoiner + " Harus Diisi Dahulu", "Warning", JOptionPane.OK_OPTION);
        return true;
    }
    
    protected final boolean IsRowValid(int row)
    {
        var result = row < TableHandler.GetRowCount() && row >= 0;
        if(!result)
            JOptionPane.showMessageDialog(null, "Row Yang Anda Masukan Tidak Valid", "Warning", JOptionPane.OK_OPTION);
        return result;
    }
    
    protected final Map<T, Object> GetValidFieldData()
    {
        return TextFieldHandler.GetTextFields().entrySet()
                .stream()
                .collect(Collectors.toMap(e -> e.getKey(), e -> e.getValue().getText()));
    }
    
    protected final Map.Entry<T, Object> GetValidTableData(int rowIndex)
    {
        return new AbstractMap.SimpleEntry<>(TableHandler.TableColumnKey, TableHandler.GetValueAt(rowIndex));
    }
    
    public abstract Map<T, Object> ValidateCreateUpdateData();
    
    public abstract Map.Entry<T, Object> ValidateDeleteData();
}