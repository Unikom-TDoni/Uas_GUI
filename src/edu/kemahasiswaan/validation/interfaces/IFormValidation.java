/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.kemahasiswaan.validation.interfaces;

import java.util.Map;

/**
 *
 * @author Theod
 * @param <TEnum>
 * @param <TMap>
 */
public interface IFormValidation<TEnum extends Enum<TEnum>, TMap extends Map<TEnum, Object>> 
{
    public TMap ValidateForm();
}