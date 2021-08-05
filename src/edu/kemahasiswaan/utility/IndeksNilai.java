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
public enum IndeksNilai {
    A("LULUS"),
    B("LULUS"),
    C("LULUS"),
    D("TIDAK LULUS"),
    E("TIDAK LULUS");
    
    public final String Keterangan;
    
    private IndeksNilai(String keterangan)
    {
        Keterangan = keterangan;
    }
}
