/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.kemahasiswaan.validation;

import javax.swing.JOptionPane;

/**
 *
 * @author Theod
 */
public abstract class Validation 
{
    protected void ShowErrorValidationMessage(String message)
    {
        JOptionPane.showMessageDialog(null, 
            message, "Error", 
            JOptionPane.INFORMATION_MESSAGE
        );
    }
}
