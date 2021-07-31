/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.kemahasiswaan.validation;

import java.util.HashMap;
import javax.swing.text.JTextComponent;
import edu.kemahasiswaan.validation.key.MahasiswaFormValidationKey;

/**
 *
 * @author Theod
 */
public final class MahasiswaFormValidation extends FormValidation<MahasiswaFormValidationKey>
{
    public MahasiswaFormValidation(HashMap<MahasiswaFormValidationKey, JTextComponent> fieldCollection) {
        super(fieldCollection);
    }
}
