/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.kemahasiswaan.handler;

import java.util.Map;
import java.util.StringJoiner;
import java.util.LinkedHashMap;
import javax.swing.text.JTextComponent;
import edu.kemahasiswaan.helper.StringCaseFormatHelper;

/**
 *
 * @author Theod
 * @param <T>
 */
public final class JTextFieldHandler<T extends Enum> 
{
    private final LinkedHashMap<T, JTextComponent> _textFields;
    
    public JTextFieldHandler(LinkedHashMap<T, JTextComponent> textFields)
    {
        _textFields = textFields;
    }
    
    public void SetAllText(Map<T, Object> textInputData)
    {
        textInputData.entrySet().forEach(item -> {
            var textComponent = _textFields.get(item.getKey());
            textComponent.setText(item.getValue().toString());
        });
    }
    
    public void ResetAllText()
    {
        _textFields.entrySet().forEach(item -> {
            item.getValue().setText("");
        });
    }
    
    public LinkedHashMap<T, String> GetTextFieldsValue()
    {
        var result = new LinkedHashMap<T, String>();
        _textFields.entrySet().forEach(item ->
        {
            result.put(item.getKey(), item.getValue().getText());
        });
        return result;
    }
    
    public final String GetNullField()
    {
        var stringJoiner = new StringJoiner(", ");
        
        _textFields.forEach((key, value) -> 
        {
            if(value.getText().equals(""))
                stringJoiner.add(StringCaseFormatHelper.GetSplitedCamelCase(key.toString(), " "));
        });
        
        return stringJoiner.toString();
    }
}
