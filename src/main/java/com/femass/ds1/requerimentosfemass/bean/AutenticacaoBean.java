/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.femass.ds1.requerimentosfemass.bean;

import com.femass.ds1.requerimentofemass.util.Constantes;
import com.femass.ds1.requerimentofemass.util.SimpleMailTemplete;
import com.femass.ds1.requerimentosfemass.dao.ResponsavelDao;
import com.femass.ds1.requerimentosfemass.model.Responsavel;
import com.outjected.email.api.MailMessage;
import com.outjected.email.impl.MailMessageImpl;
import java.io.IOException;
import java.io.StringWriter;
import java.util.Locale;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import org.apache.velocity.VelocityContext;
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
            System.out.println("Responsavel nulo = " + respLogado);
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

    /**
     * Envio de senha ppor email.
     *
     * @throws IOException
     */
    public void recuperarSenha() throws IOException {
//		FuncionarioDAO funcionarioDAO = new FuncionarioDAO();
        if (respLogado.getCpf() == null || respLogado.getCpf().equals("")) {
            Messages.addGlobalError("ERRO: >>>> Favor informar o CPF e clicar em 'ESQUECI MINHA SENHA'.");
            System.out.println("faltou informar o CPF.");
            return;
        } else {
            Responsavel resp = new Responsavel();
            resp = respDao.buscarPorCPF(respLogado.getCpf());
            if (resp != null) {
                if (resp.getEmail() != null) {
                    //configuração do email return configsession
                    SimpleMailTemplete smt = new SimpleMailTemplete();
                    MailMessage message = new MailMessageImpl(smt.enviarEmail());
                    
                    // envia variaveis para o template
                    VelocityContext context = new VelocityContext();
                    context.put("responsavel", resp);
                    
                    //prepara o conteúdo do email em html com codificação
                    StringWriter writer = smt.escreveTempate("recupera_senha.html", context);

                    message.to(resp.getEmail())
                            .subject("Pedido de Envio de senha.")
                            .bodyHtml(writer.toString())
                            .put("locale", new Locale("pt", "BR"))
                            .send();
                    
                    Messages.addGlobalInfo("Sua senha foi enviada por Email com sucesso!");
                } else {
                    Messages.addGlobalError("ERRO: >>>> Não possui email em seu cadastro, favor procurar o Desenvolvedor do Sistema.");
                    return;
                }
            } else {
                Messages.addGlobalError("ERRO: >>>> Este CPF não possui cadastro no sistema.");
                return;
            }
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
