/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.kemahasiswaan.repository;

import edu.kemahasiswaan.helper.StringCaseFormatHelper;

/**
 *
 * @author Theod
 * @param <T>
 */
public abstract class Repository<T extends Enum<T>>
{
    protected final String TableName;
    
    protected Repository(Class<T> enumClass)
    {
        TableName = StringCaseFormatHelper.GetSplitedCamelCase(enumClass.getSimpleName(), "_").toLowerCase();
    }
}
