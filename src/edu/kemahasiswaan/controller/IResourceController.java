/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.kemahasiswaan.controller;

import edu.kemahasiswaan.response.Response;

/**
 *
 * @author Theod
 * @param <T>
 */
public interface IResourceController<T extends Response> 
{
    public T Create();
    public T SelectAll();
    public T Update();
    public T Delete();
}
