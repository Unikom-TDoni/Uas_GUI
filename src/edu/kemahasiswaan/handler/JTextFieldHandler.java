/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.kemahasiswaan.handler;

import java.util.Map;
import java.util.LinkedHashMap;
import javax.swing.text.JTextComponent;

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
    
    public LinkedHashMap<T, JTextComponent> GetTextFields()
    {
        return _textFields;
    }
}
