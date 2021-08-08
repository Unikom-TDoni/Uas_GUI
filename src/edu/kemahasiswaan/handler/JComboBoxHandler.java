/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.kemahasiswaan.handler;

import java.util.Map;
import java.util.LinkedList;
import javax.swing.JComboBox;
import java.util.LinkedHashMap;

/**
 *
 * @author Theod
 */
public final class JComboBoxHandler<T> 
{
    private final LinkedHashMap<T, JComboBox> _comboBoxes;
    
    public JComboBoxHandler(LinkedHashMap<T, JComboBox> comboBoxes)
    {
        _comboBoxes = comboBoxes;
    }
    
    public void Load(Map.Entry<T, LinkedList<Object>> inputData)
    {
        JComboBox comboBox = _comboBoxes.get(inputData.getKey());
        comboBox.removeAllItems();
        inputData.getValue().forEach(item -> 
        {
            comboBox.addItem(item);
        });
    }
    
    public Object GetSelectedItem(T key)
    {
        return _comboBoxes.get(key).getSelectedItem();
    }
}
