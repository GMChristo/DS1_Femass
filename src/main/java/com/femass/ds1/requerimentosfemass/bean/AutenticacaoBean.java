/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.femass.ds1.requerimentosfemass.bean;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author Alex
 */
@ManagedBean
@SessionScoped
public class AutenticacaoBean {
    
    public String entrar(){
        return "/comum/entrada/principal.xhtml";
    }
    
    public String sair(){
        return "/comum/autenticacao/autenticacao.xhtml";
    }
}
