/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.kemahasiswaan.helper;

import java.text.NumberFormat;
import javax.swing.text.NumberFormatter;

/**
 *
 * @author Theod
 */
public class FieldNumberFormatHelper 
{
    public static NumberFormatter GetFormat(Class<?> numberValueClass, Comparable<?> maxValue)
    {
        var format = NumberFormat.getInstance();
        var formatter = new NumberFormatter(format);
        formatter.setValueClass(numberValueClass);
        formatter.setMinimum(0);
        formatter.setMaximum(maxValue);
        formatter.setAllowsInvalid(false);
        formatter.setCommitsOnValidEdit(true);
        return formatter;
    }
}
