/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.kemahasiswaan.helper;

import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 *
 * @author Theod
 */
public final class DateHelper 
{
    public static final String DefaultDateFormat = "MMMM-d-y";
    
    public static String GetFormatedDate(Date date)
    {
        return new SimpleDateFormat(DefaultDateFormat).format(date);
    }
    
    public static java.sql.Date GetSqlFormatedDate(String date) 
    {
        try
        {
            SimpleDateFormat formatter = new SimpleDateFormat(DefaultDateFormat);
            Date dateStr = formatter.parse(date);
            return new java.sql.Date(dateStr.getTime());
        }
        catch(ParseException exception)
        {
            System.out.println(exception);
            return null;
        }
    }
    
    public static int GetCurrentYear(){
        return Calendar.getInstance().get(Calendar.YEAR);
    }
}
