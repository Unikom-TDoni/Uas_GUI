/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.kemahasiswaan.response;

import java.util.Map;
import java.util.HashMap;
import edu.kemahasiswaan.table.Pengguna;

/**
 *
 * @author Theod
 */
public final class PenggunaResponse extends Response<Pengguna>
{
    public PenggunaResponse GenerateResultFromValidation(Map<Pengguna, Object> validationResult)
    {
        Result.add(validationResult);
        return this;
    }
    
    public PenggunaResponse GenerateResultFromLoginStatus(boolean loginResult)
    {
        Result.add(new HashMap<>(){{put(Pengguna.Username, loginResult);}});
        return this;
    }
}
