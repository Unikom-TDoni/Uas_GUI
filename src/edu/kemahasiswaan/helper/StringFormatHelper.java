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
public final class StringFormatHelper 
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
        var words[] = value.split(inputSeparator);
        var result = "";
 
        for(var word : words)
        {
            String firstLetter = word.substring(0,1);
            String remainingLetters = word.substring(1);
            result += firstLetter.toUpperCase() + remainingLetters + outputSeparator;
        }
        
        return result;
    }
}
