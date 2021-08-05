/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.kemahasiswaan.controller;

import edu.kemahasiswaan.repository.Repository;
import javax.swing.JOptionPane;

/**
 *
 * @author Theod
 * @param <TRepository>
 */
public abstract class Controller<TRepository extends Repository>
{
    protected TRepository Repository;
    
    protected void ShowSqlErrorMessage(Exception exception)
    {
        JOptionPane.showMessageDialog(null, 
                exception.getMessage(), "Error", 
                JOptionPane.INFORMATION_MESSAGE
            );
    }
}
