/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.kemahasiswaan.response;

import java.util.Map;
import java.util.HashMap;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import edu.kemahasiswaan.utility.IndeksNilai;
import edu.kemahasiswaan.table.NilaiMahasiswa;
import edu.kemahasiswaan.helper.NumberFormatHelper;
import edu.kemahasiswaan.helper.StringCaseFormatHelper;
import edu.kemahasiswaan.utility.KalkulatorNilaiUtility;

/**
 *
 * @author Theod
 */
public final class NilaiMahasiswaResponse extends Response<NilaiMahasiswa>
{
    private final KalkulatorNilaiUtility _kalkulatorNilaiUtility = new KalkulatorNilaiUtility();
    
    public NilaiMahasiswaResponse GenerateResultFromQuerySelect(ResultSet queryResult)
    {
        try
        {
            while (queryResult.next())
            {
                int kehadiran = queryResult.getInt(NilaiMahasiswa.Kehadiran.toString());
                int tugasPertama = queryResult.getInt(NilaiMahasiswa.TugasPertama.toString());
                int tugasKedua = queryResult.getInt(NilaiMahasiswa.TugasKedua.toString());
                int tugasKetiga = queryResult.getInt(NilaiMahasiswa.TugasKetiga.toString());
                int uts = queryResult.getInt(NilaiMahasiswa.Uts.toString());
                int uas = queryResult.getInt(NilaiMahasiswa.Uas.toString());
                
                float nilaiKehadiran = _kalkulatorNilaiUtility.CalculateNilaiAbsen(kehadiran, 5);
                float nilaiTugas = _kalkulatorNilaiUtility.CalculateNilaiTugas(tugasPertama, tugasKedua, tugasKetiga, 25);
                float nilaiUts = _kalkulatorNilaiUtility.CalculateNilaiUts(uts, 30);
                float nilaiUas = _kalkulatorNilaiUtility.CalculateNilaiUas(uas, 40);
                float nilaiAkhir = _kalkulatorNilaiUtility.CalculateNilaiAkhir(nilaiKehadiran, nilaiTugas, nilaiUts, nilaiUas);
                IndeksNilai indeks = _kalkulatorNilaiUtility.GenerateIndex(nilaiAkhir);
                String keterangan = _kalkulatorNilaiUtility.GenerateKeterangan(indeks, kehadiran);
                
                HashMap<NilaiMahasiswa, Object> rowData = new HashMap<>()
                {{
                    put(NilaiMahasiswa.No, queryResult.getInt(NilaiMahasiswa.No.toString()));
                    put(NilaiMahasiswa.NamaMhs, queryResult.getString(NilaiMahasiswa.NamaMhs.toString()));
                    put(NilaiMahasiswa.NamaMk, queryResult.getString(NilaiMahasiswa.NamaMk.toString()));
                    put(NilaiMahasiswa.Kehadiran, kehadiran);
                    put(NilaiMahasiswa.TugasPertama, tugasPertama);
                    put(NilaiMahasiswa.TugasKedua, tugasKedua);
                    put(NilaiMahasiswa.TugasKetiga, tugasKetiga);
                    put(NilaiMahasiswa.Uts, uts);
                    put(NilaiMahasiswa.Uas, uas);
                    put(NilaiMahasiswa.NilaiKehadiran, StringCaseFormatHelper.GetFloatStringFormat(nilaiKehadiran));
                    put(NilaiMahasiswa.NilaiTugas, StringCaseFormatHelper.GetFloatStringFormat(nilaiTugas));
                    put(NilaiMahasiswa.NilaiUts, StringCaseFormatHelper.GetFloatStringFormat(nilaiUts));
                    put(NilaiMahasiswa.NilaiUas, StringCaseFormatHelper.GetFloatStringFormat(nilaiUas));
                    put(NilaiMahasiswa.NilaiAkhir, StringCaseFormatHelper.GetFloatStringFormat(nilaiAkhir));
                    put(NilaiMahasiswa.Indeks, indeks);
                    put(NilaiMahasiswa.Keterangan, keterangan);
                }};
                Result.add(rowData);
            }
        }
        catch(SQLException exception)
        {
            JOptionPane.showMessageDialog(null, 
                exception.getMessage(), "Error", 
                JOptionPane.INFORMATION_MESSAGE
            );
        }
        finally
        {
            try { queryResult.close(); } catch (SQLException exception) { }
        }
        return this;
    }
    
    public NilaiMahasiswaResponse GenerateResultFromQueryCreate(ResultSet queryResult, Map<NilaiMahasiswa, Object> validationResult)
    {
        try
        {
            queryResult.next();
            var result = GenerateTableCalculatorResult(validationResult);
            result.put(NilaiMahasiswa.No, queryResult.getLong(1));
            Result.add(result);
        }
        catch(SQLException exception)
        {
            JOptionPane.showMessageDialog(null, 
                exception.getMessage(), "Error", 
                JOptionPane.INFORMATION_MESSAGE
            );
        }
        finally
        {
            try { queryResult.close(); } catch (SQLException exception) { }
        }
        return this;
    }
    
    public NilaiMahasiswaResponse GenerateResultFromQueryUpdate(Map<NilaiMahasiswa, Object> validationResult)
    {
        Result.add(GenerateTableCalculatorResult(validationResult));
        return this;
    }
    
    public NilaiMahasiswaResponse GenerateResultFromQueryDelete(Map<NilaiMahasiswa, Object> validationResult){
        Result.add(validationResult);
        return this;
    }
    
    private Map<NilaiMahasiswa, Object> GenerateTableCalculatorResult(Map<NilaiMahasiswa, Object> validationResult)
    {
        int kehadiran = NumberFormatHelper.ParseInteger(validationResult.get(NilaiMahasiswa.Kehadiran));
        int tugas1 = NumberFormatHelper.ParseInteger(validationResult.get(NilaiMahasiswa.TugasPertama));
        int tugas2 = NumberFormatHelper.ParseInteger(validationResult.get(NilaiMahasiswa.TugasKedua));
        int tugas3 = NumberFormatHelper.ParseInteger(validationResult.get(NilaiMahasiswa.TugasKetiga));
        int uts = NumberFormatHelper.ParseInteger(validationResult.get(NilaiMahasiswa.Uts));
        int uas = NumberFormatHelper.ParseInteger(validationResult.get(NilaiMahasiswa.Uas));

        float nilaiKehadiran = _kalkulatorNilaiUtility.CalculateNilaiAbsen(kehadiran, 5);
        float nilaiTugas = _kalkulatorNilaiUtility.CalculateNilaiTugas(tugas1, tugas2, tugas3 , 25);
        float nilaiUts = _kalkulatorNilaiUtility.CalculateNilaiUts(uts, 30);
        float nilaiUas = _kalkulatorNilaiUtility.CalculateNilaiUas(uas, 40);
        float nilaiAkhir = _kalkulatorNilaiUtility.CalculateNilaiAkhir(nilaiKehadiran, nilaiTugas, nilaiUts, nilaiUas);
        IndeksNilai indeks = _kalkulatorNilaiUtility.GenerateIndex(nilaiAkhir);
        String keterangan = _kalkulatorNilaiUtility.GenerateKeterangan(indeks, kehadiran);
        
        validationResult.put(NilaiMahasiswa.NilaiKehadiran, StringCaseFormatHelper.GetFloatStringFormat(nilaiKehadiran));
        validationResult.put(NilaiMahasiswa.NilaiTugas, StringCaseFormatHelper.GetFloatStringFormat(nilaiTugas));
        validationResult.put(NilaiMahasiswa.NilaiUts, StringCaseFormatHelper.GetFloatStringFormat(nilaiUts));
        validationResult.put(NilaiMahasiswa.NilaiUas, StringCaseFormatHelper.GetFloatStringFormat(nilaiUas));
        validationResult.put(NilaiMahasiswa.NilaiAkhir, StringCaseFormatHelper.GetFloatStringFormat(nilaiAkhir));
        validationResult.put(NilaiMahasiswa.Indeks, indeks);
        validationResult.put(NilaiMahasiswa.Keterangan, keterangan);
        
        return validationResult;
    }
}
