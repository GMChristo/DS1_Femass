/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.femass.ds1.requerimentosfemass.bean;

import com.femass.ds1.requerimentofemass.util.Constantes;
import com.femass.ds1.requerimentosfemass.dao.ResponsavelDao;
import com.femass.ds1.requerimentosfemass.model.Responsavel;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import org.omnifaces.util.Messages;

/**
 *
 * @author Alex
 */
@ManagedBean
@SessionScoped
public class AutenticacaoBean {

    private Responsavel respLogado;
    private Constantes constante;

    @EJB
    ResponsavelDao respDao;

    public String entrar() {
        return "/comum/entrada/principal.xhtml";
    }

    /**
     * Metodo sair
     *
     * @return
     */
    public String sair() {
        try {
            System.out.println("Usuário Deslogado...");
            respLogado = null;
            System.out.println("Responsavel nulo = "+respLogado);
            return "/comum/autenticacao/autenticacao.xhtml?faces-redirect=true";
        } catch (RuntimeException ex) {
            Messages.addGlobalError("Erro ao tentar sair do sistema: " + ex.getMessage());
            return null;
        }
    }

    /**
     * Metodo Autenticar trocado de void para string
     *
     * @return
     */
    public String autenticar() {
        try {

            respLogado = respDao.autenticar(respLogado.getCpf(), respLogado.getSenha());

            if (respLogado == null) {
                Messages.addGlobalError("CPF e/ou Senha inválidos.");
                return null;
            } else {
                Messages.addGlobalInfo("Usuário autenticado com sucesso.");
                return "/comum/entrada/principal.xhtml?faces-redirect=true";
            }
        } catch (RuntimeException ex) {
            Messages.addGlobalError("Erro ao tentar entrar no sistema: " + ex.getMessage());
            return null; // permanecer na pagina onde estou
        }
    }

    public Responsavel getRespLogado() {
        if (respLogado == null) {
            respLogado = new Responsavel();
        }
        return respLogado;
    }

    public void setRespLogado(Responsavel respLogado) {
        this.respLogado = respLogado;
    }

    public Constantes getConstante() {
        if (constante == null) {
            constante = new Constantes();
        }
        return constante;
    }

}
