/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.kemahasiswaan.utility;

/**
 *
 * @author Theod
 */
public class KalkulatorNilaiUtility 
{
    private final int _jumlahMinimumAbsen = 11;
    
    public float CalculateNilaiAbsen(float jumlahAbsensi, float precentage)
    {
        return ((jumlahAbsensi/14) * 100 * precentage)/100;
    }
    
    public float CalculateNilaiTugas(float tugas1, float tugas2, float tugas3, float precentage)
    {
        return ((tugas1+tugas2+tugas3)/3) * (precentage/100);
    }
    
    public float CalculateNilaiUts(float uts, float precnetage)
    {
        return uts * (precnetage/100);
    }
    
    public float CalculateNilaiUas(float uas, float precnetage)
    {
        return uas * (precnetage/100);
    }
    
    public float CalculateNilaiAkhir(float nilaiAbsen, float nilaiTugas, float nilaiUts, float nilaiUas)
    {
        return nilaiAbsen + nilaiTugas + nilaiUts + nilaiUas;
    }
    
    public IndeksNilai GenerateIndex(float nilaiAkhir)
    {
        if(nilaiAkhir >= 80 && nilaiAkhir <= 100)
            return IndeksNilai.A;
        else if(nilaiAkhir >= 68 && nilaiAkhir < 80)
            return IndeksNilai.B;
        else if(nilaiAkhir >= 56 && nilaiAkhir < 68)
            return IndeksNilai.C;
        else if(nilaiAkhir >= 45 && nilaiAkhir < 56)
            return IndeksNilai.D;
        else
            return IndeksNilai.E;
    }
    
    public String GenerateKeterangan(IndeksNilai indeks, float jumlahAbsensi)
    {
        if(jumlahAbsensi < _jumlahMinimumAbsen)
            return IndeksNilai.E.Keterangan;
        return indeks.Keterangan;
    }
}
