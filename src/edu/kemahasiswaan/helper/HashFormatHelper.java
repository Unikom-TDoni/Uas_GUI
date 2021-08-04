/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.kemahasiswaan.helper;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 *
 * @author Theod
 */
public final class HashFormatHelper 
{
    public static String HashSHA512(String value) 
    {
        try
        {
            var hash = MessageDigest.getInstance("SHA-512");
            hash.update(value.getBytes());
            var digest = hash.digest();
            var no = new BigInteger(1, digest);
            String hashtext = no.toString(16);
            while (hashtext.length() < 32)
                hashtext = "0" + hashtext;
            return hashtext;
        }
        catch(NoSuchAlgorithmException exception)
        {
            System.out.println(exception);
            return null;
        }
    }
}
