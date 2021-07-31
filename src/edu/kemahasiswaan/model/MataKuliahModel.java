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
public final class MataKuliahModel 
{
    private final String _no;
    private final String _nama;
    
    public MataKuliahModel(String no, String nama)
    {
        _no = no;
        _nama = nama;
    }

    public String GetNo() {
        return _no;
    }

    public String GetNama() {
        return _nama;
    }
}
