/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.kemahasiswaan.model;

/**
 *
 * @author Theod
 */
public final class MahasiswaModel 
{
    private final String _nim;
    private final String _nama;
    private final String _alamat;
    private final String _tempatLahir;
    private final String _tanggalLahir;
    
    public MahasiswaModel(String nim, String nama, String alamat, String tempatLahir, String tanggalLahir)
    {
        _nim = nim;
        _nama = nama;
        _alamat = alamat;
        _tempatLahir = tempatLahir;
        _tanggalLahir = tanggalLahir;
    }

    public String GetNim() 
    {
        return _nim;
    }

    public String GetNama() 
    {
        return _nama;
    }

    public String GetAlamat() 
    {
        return _alamat;
    }

    public String GetTempatLahir() 
    {
        return _tempatLahir;
    }

    public String GetTanggalLahir() 
    {
        return _tanggalLahir;
    }
}
