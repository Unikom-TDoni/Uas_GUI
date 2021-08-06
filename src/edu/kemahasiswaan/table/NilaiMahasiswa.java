/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.kemahasiswaan.table;

import edu.kemahasiswaan.helper.StringCaseFormatHelper;

/**
 *
 * @author Theod
 */
public enum NilaiMahasiswa 
{
    No,
    NamaMhs,
    NamaMk,
    Kehadiran,
    TugasPertama,
    TugasKedua,
    TugasKetiga,
    Uts,
    Uas,
    NilaiKehadiran,
    NilaiTugas,
    NilaiUts,
    NilaiUas,
    NilaiAkhir,
    Indeks,
    Keterangan,
    Angkatan,
    Nim,
    NoMk
    ;
    
    @Override
    public String toString() 
    {
        return StringCaseFormatHelper.GetSplitedCamelCase(super.toString(), "_").toLowerCase();
    }
}
