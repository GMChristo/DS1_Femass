/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.femass.ds1.requerimentosfemass.service;

import com.femass.ds1.requerimentosfemass.dao.AlunoDao;
import com.femass.ds1.requerimentosfemass.model.Aluno;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author gabriel
 * Webservice de dados do aluno, usando o nosso banco de dados;
 */
@Named
@Path("aluno")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class WsAluno {

    @EJB
    AlunoDao daoAluno;
    
    @GET
    @Path("buscarDadosAluno/{login}/{senha}")
    public Aluno buscarDadosAluno(@PathParam("login") String strLogin, @PathParam("senha") String strSenha) {
        return daoAluno.getLogin(strLogin, strSenha);
    }
    @GET
    @Path("buscarDados")
    public Aluno buscarDados() {
        return daoAluno.getLogin("1701130027", "GMC4620");
    }
    
    
    
}