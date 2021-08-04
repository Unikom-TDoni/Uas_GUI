/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.kemahasiswaan.response;

import java.util.Map;
import java.util.LinkedList;

/**
 *
 * @author Theod
 * @param <T>
 */
public abstract class Response<T extends Enum> 
{
    protected LinkedList<Map<T, Object>> Result = new LinkedList<>();
    
    public LinkedList<Map<T, Object>> GetResult()
    {
        return Result;
    }  
}
