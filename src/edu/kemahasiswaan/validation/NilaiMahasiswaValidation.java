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
import edu.kemahasiswaan.table.NilaiMahasiswa;
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
public class NilaiMahasiswaValidation extends Validation implements 
        IFormValidation<NilaiMahasiswa, Map<NilaiMahasiswa, Object>>,
        ITableValidation<NilaiMahasiswa, Map.Entry<NilaiMahasiswa,Object>>
{
    private final JTableHandler _tableHandler;
    private final JComboBoxHandler _comboBoxHandler;
    private final JTextFieldHandler _textFieldHandler;
    
    public NilaiMahasiswaValidation(JTableHandler tableHandler, JTextFieldHandler textFieldHandler, JComboBoxHandler comboBoxHandler) 
    {
        _tableHandler = tableHandler;
        _textFieldHandler = textFieldHandler;
        _comboBoxHandler = comboBoxHandler;
    }
    
    @Override
    public Map<NilaiMahasiswa, Object> ValidateForm() 
    {
        Map<NilaiMahasiswa, Object> result;
        String nullField = _textFieldHandler.GetEmptyFieldName();
        if(nullField.length() != 0)
        {
            ShowErrorValidationMessage("Maaf, text field " + nullField + "masih kosong silahkan isi terlebih dahulu");
            return Collections.emptyMap();
        }
        
        result = new LinkedHashMap<>(_textFieldHandler.GetTextFields());
        
        String notValidNilaiField = GetNotValidNilaiField(new LinkedHashMap<>(){{
            put(NilaiMahasiswa.Uts , result.get(NilaiMahasiswa.Uts).toString());
            put(NilaiMahasiswa.Uas , result.get(NilaiMahasiswa.Uas).toString());
            put(NilaiMahasiswa.TugasKedua , result.get(NilaiMahasiswa.TugasKedua).toString());
            put(NilaiMahasiswa.TugasKetiga , result.get(NilaiMahasiswa.TugasKetiga).toString());
            put(NilaiMahasiswa.TugasPertama, result.get(NilaiMahasiswa.TugasPertama).toString());
        }});
        
        if(notValidNilaiField.length() != 0)
        {
            ShowErrorValidationMessage("Maaf, text field " + notValidNilaiField + "harus memiliki nilai kurang dari 100");
            return Collections.emptyMap();
        }
        
        if(Integer.valueOf(result.get(NilaiMahasiswa.Kehadiran).toString())  > 14){
            ShowErrorValidationMessage("Maaf, maksimal kehadiran yang bisa anda masukan adalah 14");
            return Collections.emptyMap();
        }
         
        result.put(NilaiMahasiswa.NamaMhs, _comboBoxHandler.GetSelectedItem(NilaiMahasiswa.NamaMhs));
        result.put(NilaiMahasiswa.NamaMk, _comboBoxHandler.GetSelectedItem(NilaiMahasiswa.NamaMk));
        if(_tableHandler.GetSelectedRowIndex() != -1)
            result.put(NilaiMahasiswa.No, _tableHandler.GetValueAt(NilaiMahasiswa.No, _tableHandler.GetSelectedRowIndex()));
        
        return result;
    }

    @Override
    public Map.Entry<NilaiMahasiswa, Object> ValidateTable() 
    {
        int rowIndex = _tableHandler.GetSelectedRowIndex();
        if(!_tableHandler.IsRowValid(rowIndex))
        {
            ShowErrorValidationMessage("Maaf, silahkan pilih baris pada tabel Nilai terlebih dahulu");
            return new AbstractMap.SimpleEntry<>(null, null);
        }
        return new AbstractMap.SimpleEntry<>((NilaiMahasiswa)_tableHandler.TableColumnKey, _tableHandler.GetValueAt(_tableHandler.TableColumnKey, rowIndex));
    }
    
    private String GetNotValidNilaiField(LinkedHashMap<NilaiMahasiswa, String> nilaiTextFields)
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
