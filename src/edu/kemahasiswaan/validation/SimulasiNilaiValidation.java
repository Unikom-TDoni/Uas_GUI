/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.kemahasiswaan.validation;

import java.util.Map;
import java.util.Collections;
import java.util.AbstractMap;
import java.util.StringJoiner;
import java.util.LinkedHashMap;
import edu.kemahasiswaan.table.SimulasiNilai;
import edu.kemahasiswaan.handler.JTableHandler;
import edu.kemahasiswaan.handler.JComboBoxHandler;
import edu.kemahasiswaan.handler.JTextFieldHandler;
import edu.kemahasiswaan.helper.StringCaseFormatHelper;
import edu.kemahasiswaan.validation.interfaces.IFormValidation;
import edu.kemahasiswaan.validation.interfaces.ITableValidation;

/**
 *
 * @author Theod
 */
public final class SimulasiNilaiValidation extends Validation implements 
        IFormValidation<SimulasiNilai, Map<SimulasiNilai,Object>>, 
        ITableValidation<SimulasiNilai, Map.Entry<SimulasiNilai, Object>>
{
    private final JTableHandler _tableHandler;
    private final JComboBoxHandler _comboBoxHandler;
    private final JTextFieldHandler _textFieldHandler;
    
    public SimulasiNilaiValidation(JTableHandler tableHandler, JTextFieldHandler textFieldHandler, JComboBoxHandler comboBoxHandler) 
    {
        _tableHandler = tableHandler;
        _textFieldHandler = textFieldHandler;
        _comboBoxHandler = comboBoxHandler;
    }
    
    @Override
    public Map<SimulasiNilai, Object> ValidateForm() 
    {
        Map<SimulasiNilai, Object> result;
        String nullField = _textFieldHandler.GetEmptyFieldName();
        if(nullField.length() != 0)
        {
            ShowErrorValidationMessage("Maaf, text field " + nullField + "masih kosong silahkan isi terlebih dahulu");
            return Collections.emptyMap();
        }
               
        result = new LinkedHashMap<>(_textFieldHandler.GetTextFields());
        String notValidNilaiField = GetNotValidNilaiField(new LinkedHashMap<>(){{
            put(SimulasiNilai.Uts , result.get(SimulasiNilai.Uts).toString());
            put(SimulasiNilai.Uas , result.get(SimulasiNilai.Uas).toString());
            put(SimulasiNilai.TugasKedua , result.get(SimulasiNilai.TugasKedua).toString());
            put(SimulasiNilai.TugasKetiga , result.get(SimulasiNilai.TugasKetiga).toString());
            put(SimulasiNilai.TugasPertama, result.get(SimulasiNilai.TugasPertama).toString());
        }});
        
        if(notValidNilaiField.length() != 0)
        {
            ShowErrorValidationMessage("Maaf, text field " + notValidNilaiField + "harus memiliki nilai kurang dari 100");
            return Collections.emptyMap();
        }
        
        boolean isPresentaseNilaiValid = IsPresentaseNilaiValid(
                result.get(SimulasiNilai.PresentaseKehadiran).toString(),
                result.get(SimulasiNilai.PresentaseUts).toString(),
                result.get(SimulasiNilai.PresentaseUas).toString(),
                result.get(SimulasiNilai.PresentaseTugas).toString()
        );
        
        if(!isPresentaseNilaiValid)
        {
            ShowErrorValidationMessage("Maaf, total presentase harus memiliki nilai 100");
            return Collections.emptyMap();
        }
        
        if(Integer.valueOf(result.get(SimulasiNilai.Kehadiran).toString())  > 14){
            ShowErrorValidationMessage("Maaf, maksimal kehadiran yang bisa anda masukan adalah 14");
            return Collections.emptyMap();
        }
        
        result.put(SimulasiNilai.NamaMk, _comboBoxHandler.GetSelectedItem(SimulasiNilai.NamaMk));
        if(_tableHandler.GetSelectedRowIndex() != -1)
            result.put(SimulasiNilai.No, _tableHandler.GetValueAt(SimulasiNilai.No, _tableHandler.GetSelectedRowIndex()));
        return result;
    }

    @Override
    public Map.Entry<SimulasiNilai, Object> ValidateTable() 
    {
        int rowIndex = _tableHandler.GetSelectedRowIndex();
        if(!_tableHandler.IsRowValid(rowIndex))
        {
            ShowErrorValidationMessage("Maaf, silahkan pilih baris pada tabel Simulasi Nilai terlebih dahulu");
            return new AbstractMap.SimpleEntry<>(null, null);
        }
        return new AbstractMap.SimpleEntry<>((SimulasiNilai)_tableHandler.TableColumnKey, _tableHandler.GetValueAt(_tableHandler.TableColumnKey, rowIndex));
    }
    
    private boolean IsPresentaseNilaiValid(String... presentaseTeks)
    {
        int result = 0;
        for (String item : presentaseTeks)
            result += Integer.valueOf(item);
        return result == 100;
    }
    
    private String GetNotValidNilaiField(LinkedHashMap<SimulasiNilai, String> nilaiTextFields)
    {
        StringJoiner stringJoiner = new StringJoiner(", ");
        nilaiTextFields.entrySet().forEach(item -> 
        {
            if(Integer.valueOf(item.getValue()) > 100){
                stringJoiner.add(StringCaseFormatHelper.GetTitleCase(item.getKey().toString(), "_", " "));
            }
        });
        return stringJoiner.toString();
    }
    
}