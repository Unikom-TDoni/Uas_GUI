/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.kemahasiswaan.helper;

/**
 *
 * @author Theod
 */
public final class StringCaseFormatHelper 
{
    public static String GetSplitedCamelCase(String value, String separator) 
    {
       return value.replaceAll(
          String.format("%s|%s|%s",
             "(?<=[A-Z])(?=[A-Z][a-z])",
             "(?<=[^A-Z])(?=[A-Z])",
             "(?<=[A-Za-z])(?=[^A-Za-z])"
          ),
          separator
       );
    }

    public static String GetTitleCase(String value, String inputSeparator, String outputSeparator)
    {
        String words[] = value.split(inputSeparator);
        String result = "";
        for(String word : words)
        {
            String firstLetter = word.substring(0,1);
            String remainingLetters = word.substring(1);
            result += firstLetter.toUpperCase() + remainingLetters + outputSeparator;
        }
        return result;
    }
    
    public static String GetFloatStringFormat(Float value){
        return String.format("%.2f", value);
    }
}
