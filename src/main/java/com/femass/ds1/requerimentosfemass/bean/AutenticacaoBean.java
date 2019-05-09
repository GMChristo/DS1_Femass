package com.femass.ds1.requerimentosfemass.bean;

import com.femass.ds1.requerimentosfemass.util.Constantes;
import com.femass.ds1.requerimentosfemass.util.SimpleMailTemplete;
import com.femass.ds1.requerimentosfemass.dao.AlunoDao;
import com.femass.ds1.requerimentosfemass.dao.CursoDao;
import com.femass.ds1.requerimentosfemass.dao.EnderecoDao;
import com.femass.ds1.requerimentosfemass.dao.ResponsavelDao;
import com.femass.ds1.requerimentosfemass.model.Aluno;
import com.femass.ds1.requerimentosfemass.model.AlunoSerFemass;
import com.femass.ds1.requerimentosfemass.model.Endereco;
import com.femass.ds1.requerimentosfemass.model.Responsavel;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.outjected.email.api.MailMessage;
import com.outjected.email.impl.MailMessageImpl;
import java.io.IOException;
import java.io.StringWriter;
import java.util.Locale;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;
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
    private Aluno aluLogado;
    boolean responsavel;
    private String cpf_mat;
    private String senha;
    private Constantes constante;

    @EJB
    ResponsavelDao respDao;

    @EJB
    AlunoDao aluDao;

    @EJB
    EnderecoDao enderecoDao;

    @EJB
    CursoDao cursoDao;

    /**
     * Metodo sair
     *
     * @return
     */
    public String sair() {
        try {
            System.out.println("Usuário Deslogado...");
            respLogado = null;
            aluLogado = null;
            System.out.println("Responsavel ou aluno nulo");
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
            if (responsavel) {
                respLogado = respDao.autenticar(cpf_mat, senha);

                if (respLogado == null) {
                    Messages.addGlobalError("CPF e/ou Senha inválidos.");
                    return null;
                } else {
                    Messages.addGlobalInfo("Usuário autenticado com sucesso.");
                    cpf_mat = "";
                    senha = "";
                    return "/comum/entrada/principal.xhtml?faces-redirect=true";
                }
            } else {
                aluLogado = aluDao.autenticar(cpf_mat, senha);

                if (aluLogado == null) {
                    // tentar buscar do web service do web academico da femass
                    AlunoSerFemass alunoFemass = jsonAluno();

                    if (alunoFemass == null) {
                        Messages.addGlobalError("Matrícula e/ou Senha inválidos.");
                        return null;
                    } else {
                        System.out.println(alunoFemass.getCpf() + " - " + alunoFemass.getCurso());
                        Aluno aluno = new Aluno();
//                        aluno.setNome(alunoFemass.getNome());
                        aluno.setNome("teste - " + alunoFemass.getCr());
                        aluno.setSenha(senha);
                        aluno.setMatricula(cpf_mat);
                        aluno.setCpf(alunoFemass.getCpf());
                        aluno.setCr(alunoFemass.getCr());
                        aluno.setEmail(alunoFemass.getEmail());
                        aluno.setCurso(cursoDao.BuscarPorNome(alunoFemass.getCurso()));

                        Endereco endereco = enderecoDao.BuscarPorLogradouroBairroAndNumero(alunoFemass.getEndereco(), alunoFemass.getEnderecoNumero(), alunoFemass.getEnderecoBairro());
                        if (endereco == null) {
                            endereco = new Endereco();
                            endereco.setLogradouro(alunoFemass.getEndereco());
                            endereco.setNumero(alunoFemass.getEnderecoNumero());
                            endereco.setBairro(alunoFemass.getEnderecoBairro());
                            endereco.setCep(alunoFemass.getEnderecoCep());
                            endereco.setCidade(alunoFemass.getEnderecoCidade());
                            endereco.setComplemento(alunoFemass.getEnderecoComplemento());
                            enderecoDao.incluir(endereco);
                            System.out.println(endereco);
                            aluno.setEndereco(endereco);
                        }
                        aluDao.incluir(aluno);
                        aluLogado = aluno;
                        
                        Messages.addGlobalInfo("Usuário autenticado com sucesso.");
                        cpf_mat = "";
                        senha = "";
                        return "/comum/entrada/principal_aluno.xhtml?faces-redirect=true";
                    }
                } else {
                    Messages.addGlobalInfo("Usuário autenticado com sucesso.");
                    cpf_mat = "";
                    senha = "";
                    return "/comum/entrada/principal_aluno.xhtml?faces-redirect=true";
                }
            }
        } catch (RuntimeException ex) {
            Messages.addGlobalError("Erro ao tentar entrar no sistema: " + ex.getMessage());
            return null; // permanecer na pagina onde estou
        }
    }

    /**
     * Método que consulta no web service as informações informadas e retorna o
     * objeto consultado
     *
     * @return
     */
    private AlunoSerFemass jsonAluno() {
        try {
            Client cliente = ClientBuilder.newClient();
            String path = "http://200.159.247.135:8083/WebAcademico/rest/usuario/buscarPorLoginSenha/" + cpf_mat + "/" + senha;
            WebTarget caminho = cliente.target(path);

            // recebendo o json da consulta
            String json = caminho.request().get(String.class);
//            System.out.println(json);

            // convertendo em classe de objeto
            Gson gson = new Gson();
            AlunoSerFemass aluno = gson.fromJson(json, AlunoSerFemass.class);
//            System.out.println(aluno);

            return aluno;
        } catch (RuntimeException ex) {
            System.out.println("Erro ao tentar Acessar o WebService da Femass - " + ex.getMessage());
            return null;
        }
    }

    /**
     * Envio de senha ppor email.
     *
     * @throws IOException
     */
    public void recuperarSenha() throws IOException {
        String email;
        // envia variaveis para o template
        VelocityContext context = new VelocityContext();

        if (cpf_mat == null || cpf_mat.equals("")) {
            Messages.addGlobalError("ERRO: >>>> Favor informar o CPF ou MATRÍCULA e clicar em 'ESQUECI MINHA SENHA'.");
            System.out.println("faltou informar o CPF OU MATRÍCULA.");
            return;
        } else {

            if (responsavel) {
                Responsavel resp = new Responsavel();
                resp = respDao.buscarPorCPF(cpf_mat);
                if (resp != null) {
                    email = resp.getEmail();
                    context.put("usuario", resp);
                    if (resp.getEmail().equals("")) {
                        Messages.addGlobalError("ERRO: >>>> Não possui email em seu cadastro, favor procurar o Desenvolvedor do Sistema.");
                        return;
                    }
                } else {
                    Messages.addGlobalError("ERRO: >>>> Este CPF não possui cadastro no sistema.");
                    return;
                }

            } else {
                Aluno aluno = new Aluno();
                aluno = aluDao.buscarPorMat(cpf_mat);
                if (aluno != null) {
                    email = aluno.getEmail();
                    context.put("usuario", aluno);
                    if (aluno.getEmail().equals("")) {
                        Messages.addGlobalError("ERRO: >>>> Não possui email em seu cadastro, favor procurar o Desenvolvedor do Sistema.");
                        return;
                    }
                } else {
                    Messages.addGlobalError("ERRO: >>>> Esta MATRÍCULA não possui cadastro no sistema.");
                    return;
                }
            }

            //configuração do email return configsession
            SimpleMailTemplete smt = new SimpleMailTemplete();
            MailMessage message = new MailMessageImpl(smt.enviarEmail());

            //prepara o conteúdo do email em html com codificação
            StringWriter writer = smt.escreveTempate("recupera_senha.html", context);

            message.to(email)
                    .subject("Pedido de Envio de senha.")
                    .bodyHtml(writer.toString())
                    .put("locale", new Locale("pt", "BR"))
                    .send();

            Messages.addGlobalInfo("Sua senha foi enviada por Email com sucesso!");
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

    public Aluno getAluLogado() {
        if (aluLogado == null) {
            aluLogado = new Aluno();
        }
        return aluLogado;
    }

    public void setAluLogado(Aluno aluLogado) {
        this.aluLogado = aluLogado;
    }

    public boolean isResponsavel() {
        return responsavel;
    }

    public void setResponsavel(boolean responsavel) {
        this.responsavel = responsavel;
    }

    public String getCpf_mat() {
        return cpf_mat;
    }

    public void setCpf_mat(String cpf_mat) {
        this.cpf_mat = cpf_mat;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
}
