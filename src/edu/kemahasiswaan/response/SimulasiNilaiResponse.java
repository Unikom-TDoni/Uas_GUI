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
import edu.kemahasiswaan.table.SimulasiNilai;
import edu.kemahasiswaan.utility.IndeksNilai;
import edu.kemahasiswaan.helper.NumberFormatHelper;
import edu.kemahasiswaan.helper.StringCaseFormatHelper;
import edu.kemahasiswaan.utility.KalkulatorNilaiUtility;

/**
 *
 * @author Theod
 */
public final class SimulasiNilaiResponse extends Response<SimulasiNilai> 
{
    private final KalkulatorNilaiUtility _kalkulatorNilaiUtility = new KalkulatorNilaiUtility();
    
    public SimulasiNilaiResponse GenerateResultFromQuerySelect(ResultSet queryResult)
    {
        try
        {
            while (queryResult.next())
            {
                int kehadiran = queryResult.getInt(SimulasiNilai.Kehadiran.toString());
                int tugasPertama = queryResult.getInt(SimulasiNilai.TugasPertama.toString());
                int tugasKedua = queryResult.getInt(SimulasiNilai.TugasKedua.toString());
                int tugasKetiga = queryResult.getInt(SimulasiNilai.TugasKetiga.toString());
                int uts = queryResult.getInt(SimulasiNilai.Uts.toString());
                int uas = queryResult.getInt(SimulasiNilai.Uas.toString());
                
                int presentaseKehadiran = queryResult.getInt(SimulasiNilai.PresentaseKehadiran.toString());
                int presentaseTugas = queryResult.getInt(SimulasiNilai.PresentaseTugas.toString());
                int presentaseUts = queryResult.getInt(SimulasiNilai.PresentaseUts.toString());
                int presentaseUas = queryResult.getInt(SimulasiNilai.PresentaseUas.toString());

                float nilaiKehadiran = _kalkulatorNilaiUtility.CalculateNilaiAbsen(kehadiran, presentaseKehadiran);
                float nilaiTugas = _kalkulatorNilaiUtility.CalculateNilaiTugas(tugasPertama, tugasKedua, tugasKetiga, presentaseTugas);
                float nilaiUts = _kalkulatorNilaiUtility.CalculateNilaiUts(uts, presentaseUts);
                float nilaiUas = _kalkulatorNilaiUtility.CalculateNilaiUas(uas, presentaseUas);
                float nilaiAkhir = _kalkulatorNilaiUtility.CalculateNilaiAkhir(nilaiKehadiran, nilaiTugas, nilaiUts, nilaiUas);
                IndeksNilai indeks = _kalkulatorNilaiUtility.GenerateIndex(nilaiAkhir);
                String keterangan = _kalkulatorNilaiUtility.GenerateKeterangan(indeks, kehadiran);
                
                HashMap<SimulasiNilai, Object> rowData = new HashMap<>()
                {{
                    put(SimulasiNilai.No, queryResult.getInt(SimulasiNilai.No.toString()));
                    put(SimulasiNilai.NamaMk, queryResult.getString(SimulasiNilai.NamaMk.toString()));
                    put(SimulasiNilai.Kehadiran, kehadiran);
                    put(SimulasiNilai.TugasPertama, tugasPertama);
                    put(SimulasiNilai.TugasKedua, tugasKedua);
                    put(SimulasiNilai.TugasKetiga, tugasKetiga);
                    put(SimulasiNilai.PresentaseUas, presentaseUas);
                    put(SimulasiNilai.PresentaseTugas, presentaseTugas);
                    put(SimulasiNilai.PresentaseKehadiran, presentaseKehadiran);
                    put(SimulasiNilai.PresentaseUts, presentaseUts);
                    put(SimulasiNilai.Uts, uts);
                    put(SimulasiNilai.Uas, uas);
                    put(SimulasiNilai.NilaiKehadiran, StringCaseFormatHelper.GetFloatStringFormat(nilaiKehadiran));
                    put(SimulasiNilai.NilaiTugas, StringCaseFormatHelper.GetFloatStringFormat(nilaiTugas));
                    put(SimulasiNilai.NilaiUts, StringCaseFormatHelper.GetFloatStringFormat(nilaiUts));
                    put(SimulasiNilai.NilaiUas, StringCaseFormatHelper.GetFloatStringFormat(nilaiUas));
                    put(SimulasiNilai.NilaiAkhir, StringCaseFormatHelper.GetFloatStringFormat(nilaiAkhir));
                    put(SimulasiNilai.Indeks, indeks);
                    put(SimulasiNilai.Keterangan, keterangan);
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
    
    public SimulasiNilaiResponse GenerateResultFromQueryCreate(ResultSet queryResult, Map<SimulasiNilai, Object> validationResult)
    {
        try
        {
            queryResult.next();
            var result = GenerateTableCalculatorResult(validationResult);
            result.put(SimulasiNilai.No, queryResult.getLong(1));
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
    
    public SimulasiNilaiResponse GenerateResultFromQueryUpdate(Map<SimulasiNilai, Object> validationResult)
    {
        Result.add(GenerateTableCalculatorResult(validationResult));
        return this;
    }
    
    public SimulasiNilaiResponse GenerateResultFromQueryDelete(Map<SimulasiNilai, Object> validationResult){
        Result.add(validationResult);
        return this;
    }
    
    private Map<SimulasiNilai, Object> GenerateTableCalculatorResult(Map<SimulasiNilai, Object> validationResult)
    {
        int presentaseKehadiran = NumberFormatHelper.ParseInteger(validationResult.get(SimulasiNilai.PresentaseKehadiran));
        int presentaseTugas = NumberFormatHelper.ParseInteger(validationResult.get(SimulasiNilai.PresentaseTugas));
        int presentaseUts = NumberFormatHelper.ParseInteger(validationResult.get(SimulasiNilai.PresentaseUts));
        int presentaseUas = NumberFormatHelper.ParseInteger(validationResult.get(SimulasiNilai.PresentaseUas));
        int kehadiran = NumberFormatHelper.ParseInteger(validationResult.get(SimulasiNilai.Kehadiran));
        int tugas1 = NumberFormatHelper.ParseInteger(validationResult.get(SimulasiNilai.TugasPertama));
        int tugas2 = NumberFormatHelper.ParseInteger(validationResult.get(SimulasiNilai.TugasKedua));
        int tugas3 = NumberFormatHelper.ParseInteger(validationResult.get(SimulasiNilai.TugasKetiga));
        int uts = NumberFormatHelper.ParseInteger(validationResult.get(SimulasiNilai.Uts));
        int uas = NumberFormatHelper.ParseInteger(validationResult.get(SimulasiNilai.Uas));

        float nilaiKehadiran = _kalkulatorNilaiUtility.CalculateNilaiAbsen(kehadiran, presentaseKehadiran);
        float nilaiTugas = _kalkulatorNilaiUtility.CalculateNilaiTugas(tugas1, tugas2, tugas3 , presentaseTugas);
        float nilaiUts = _kalkulatorNilaiUtility.CalculateNilaiUts(uts, presentaseUts);
        float nilaiUas = _kalkulatorNilaiUtility.CalculateNilaiUas(uas, presentaseUas);
        float nilaiAkhir = _kalkulatorNilaiUtility.CalculateNilaiAkhir(nilaiKehadiran, nilaiTugas, nilaiUts, nilaiUas);
        IndeksNilai indeks = _kalkulatorNilaiUtility.GenerateIndex(nilaiAkhir);
        String keterangan = _kalkulatorNilaiUtility.GenerateKeterangan(indeks, kehadiran);
        
        validationResult.put(SimulasiNilai.NilaiKehadiran, StringCaseFormatHelper.GetFloatStringFormat(nilaiKehadiran));
        validationResult.put(SimulasiNilai.NilaiTugas, StringCaseFormatHelper.GetFloatStringFormat(nilaiTugas));
        validationResult.put(SimulasiNilai.NilaiUts, StringCaseFormatHelper.GetFloatStringFormat(nilaiUts));
        validationResult.put(SimulasiNilai.NilaiUas, StringCaseFormatHelper.GetFloatStringFormat(nilaiUas));
        validationResult.put(SimulasiNilai.NilaiAkhir, StringCaseFormatHelper.GetFloatStringFormat(nilaiAkhir));
        validationResult.put(SimulasiNilai.Indeks, indeks);
        validationResult.put(SimulasiNilai.Keterangan, keterangan);
        
        return validationResult;
    }
}
