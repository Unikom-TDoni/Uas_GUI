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
            JTextComponent textComponent = _textFields.get(item.getKey());
            if(textComponent != null)
                textComponent.setText(item.getValue().toString());
        });
    }
    
    public void ResetAllText()
    {
        _textFields.entrySet().forEach(item -> {
            item.getValue().setText("");
        });
    }
    
    public JTextComponent GetTextField(T key)
    {
        return _textFields.get(key);
    }
    
    public LinkedHashMap<T, String> GetTextFields()
    {
        LinkedHashMap<T, String> result = new LinkedHashMap<>();
        _textFields.entrySet().forEach(item ->
        {
            result.put(item.getKey(), item.getValue().getText());
        });
        return result;
    }
    
    public String GetEmptyFieldName()
    {
        StringJoiner stringJoiner = new StringJoiner(", ");
        
        _textFields.forEach((key, value) -> 
        {
            if(value.getText().equals(""))
                stringJoiner.add(StringCaseFormatHelper.GetTitleCase(key.toString(), "_", " "));
        });
        
        return stringJoiner.toString();
    }
    
    public void PreventFieldHaveNonNumberCharacter(java.awt.event.KeyEvent evt, T key, int maxTextLength)
    {
        if(!Character.isDigit(evt.getKeyChar()) || _textFields.get(key).getText().length() >= maxTextLength)
            evt.consume();
    }
}
