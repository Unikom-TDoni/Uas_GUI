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
public enum MataKuliah 
{
    No,
    Nama;
    
    @Override
    public String toString() 
    {
        return StringCaseFormatHelper.GetSplitedCamelCase(super.toString().toLowerCase(), "_");
    }
}
