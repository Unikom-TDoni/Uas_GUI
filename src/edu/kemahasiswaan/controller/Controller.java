/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.kemahasiswaan.controller;

import edu.kemahasiswaan.response.Response;
import edu.kemahasiswaan.repository.Repository;
import edu.kemahasiswaan.validation.Validation;

/**
 *
 * @author Theod
 * @param <TValidation>
 * @param <TRepository>
 */
public abstract class Controller<TValidation extends Validation, TRepository extends Repository, TResponse extends Response>
{
    protected TRepository Repository;
    protected final TValidation Validation;

    public Controller(TValidation validation) 
    {
        Validation = validation;
    }

    public abstract TResponse Create();
    public abstract TResponse SelectAll();
    public abstract TResponse Update();
    public abstract TResponse Delete();
}
