/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.kemahasiswaan.database.table;

import edu.kemahasiswaan.helper.StringFormatHelper;

public enum Mahasiswa
{
    Nim,
    Nama,
    TempatLahir,
    TanggalLahir,
    Alamat;

    @Override
    public String toString() 
    {
        return StringFormatHelper.GetSplitedCamelCase(super.toString(), "_").toLowerCase();
    }
}