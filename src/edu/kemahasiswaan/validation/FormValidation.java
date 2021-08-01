/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.kemahasiswaan.validation;

import edu.kemahasiswaan.helper.PascalCaseFormatHelper;
import java.util.HashMap;
import java.util.StringJoiner;
import javax.swing.JOptionPane;
import javax.swing.text.JTextComponent;

/**
 *
 * @author Theod
 * @param <T>
 */
public abstract class FormValidation<T extends Enum> 
{
    private final HashMap<T, JTextComponent> _fieldCollection;
    
    public FormValidation(HashMap<T, JTextComponent> fieldCollection)
    {
        _fieldCollection = fieldCollection;
    }
    
    public void FormNullValidation()
    {
        var stringJoiner = new StringJoiner(", ");
        _fieldCollection.forEach((key, value) -> {
            if(value.getText().equals(""))
                stringJoiner.add(PascalCaseFormatHelper.SplitCamelCase(key.toString()));
        });
        
        var result = stringJoiner.toString();
        if(result == null)
            JOptionPane.showMessageDialog(null, "Field " + result + " Harus Diisi Dahulu", "Warning", JOptionPane.OK_OPTION);
    }
}
