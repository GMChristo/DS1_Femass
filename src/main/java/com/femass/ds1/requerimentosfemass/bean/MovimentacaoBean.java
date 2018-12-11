package com.femass.ds1.requerimentosfemass.bean;

import com.femass.ds1.requerimentosfemass.dao.MovimentacaoDao;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.event.SelectEvent;

import com.femass.ds1.requerimentosfemass.model.Movimentacao;
import com.femass.ds1.requerimentosfemass.filter.RequerimentoFilter;
import com.femass.ds1.requerimentosfemass.model.Requerimento;
import com.femass.ds1.requerimentosfemass.model.Responsavel;
import com.femass.ds1.requerimentosfemass.model.StatusRequerimento;
import java.util.Arrays;
import java.util.Date;
import javax.ejb.EJB;
import javax.faces.bean.ManagedProperty;
import org.omnifaces.util.Messages;
import org.primefaces.event.FlowEvent;

@ManagedBean
@ViewScoped
public class MovimentacaoBean {

    private Movimentacao cadastro;
    private List<Movimentacao> lista;
    private List<Movimentacao> lipesq;
    private int size;
    private String acao;
    private RequerimentoFilter filtro;
    @ManagedProperty(value = "#{autenticacaoBean}")
    private AutenticacaoBean autenticacaoBean;
    private boolean liTodos;
    private boolean skip;
    private List<StatusRequerimento> liStatusReq;
 
    @EJB
    MovimentacaoDao dao;

    /**
     * Metodo de abertura carrega somente as movimentações do responsavel
     * logado.
     */
    public void carregar() {
        try {
            if (liTodos == true) {
                lista = dao.getMovAbertas();
            } else {
                lista = dao.getMovAbertasPorResponsavel(autenticacaoBean.getRespLogado());
            }
            size = lista.size();
            liStatusReq = Arrays.asList(StatusRequerimento.values());
        } catch (RuntimeException e) {
            System.out.println(">>>> Erro ao tentar gerar a lista de movimentações abertas.");
        }
    }

    /**
     * Metodo de selecao do data table pela barra de botoes
     *
     * @param evento
     */
    public void onRowSelect(SelectEvent evento) {
        cadastro = (Movimentacao) evento.getObject();
        acao = "Editar";
    }

    /**
     * Metodo Bean novo - Cria um novo objeto.
     */
    public void novo() {
        cadastro = new Movimentacao();
        acao = "Salvar";
    }

    /**
     * Registra a data e hora que o responsável recebeu o requerimento.
     */
    public void receber() {
        try {
            if (cadastro.getDataRecebimento() != null) {
                Messages.addGlobalError(">>>> ERRO: esta movimentação já foi recebida.");
                return;
            } else {
                Responsavel resp = autenticacaoBean.getRespLogado();
                if (cadastro.getResponsavel().equals(resp)) {
                    cadastro.setDataRecebimento(new Date());
                    dao.alterar(cadastro);
                    cadastro = new Movimentacao();
                } else {
                    Messages.addGlobalError(">>>> ERRO: Você não é responsável por este CURSO.");
                    return;
                }
                Messages.addGlobalInfo("Movimentação recebida com sucesso!");
            }
        } catch (RuntimeException e) {
            Messages.addGlobalError(">>>> ERRO: Não foi possivel registrar o recebimento da movimentação" + e.getMessage());
        }
    }

    /**
     * Método fechar
     */
    public void fechar() {
        cadastro = new Movimentacao();
        acao = "";
    }

    public String onFlowProcess(FlowEvent event) {
        if (skip) {
            skip = false; // reset in case user goes back
            return "conclusao";
        } else {
            return event.getNewStep();
        }
    }

    public Movimentacao getCadastro() {
        if (cadastro == null) {
            cadastro = new Movimentacao();
        }
        return cadastro;
    }

    public void setCadastro(Movimentacao cadastro) {
        this.cadastro = cadastro;
    }

    public List<Movimentacao> getLista() {
        return lista;
    }

    public void setLista(List<Movimentacao> lista) {
        this.lista = lista;
    }

    public List<Movimentacao> getLipesq() {
        return lipesq;
    }

    public void setLipesq(List<Movimentacao> lipesq) {
        this.lipesq = lipesq;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String getAcao() {
        return acao;
    }

    public RequerimentoFilter getFiltro() {
        if (filtro == null) {
            filtro = new RequerimentoFilter();
        }
        return filtro;
    }

    public void setFiltro(RequerimentoFilter filtro) {
        this.filtro = filtro;
    }

    public AutenticacaoBean getAutenticacaoBean() {
        return autenticacaoBean;
    }

    public void setAutenticacaoBean(AutenticacaoBean autenticacaoBean) {
        this.autenticacaoBean = autenticacaoBean;
    }

    public boolean isLiTodos() {
        return liTodos;
    }

    public void setLiTodos(boolean liTodos) {
        this.liTodos = liTodos;
    }

    public boolean isSkip() {
        return skip;
    }

    public void setSkip(boolean skip) {
        this.skip = skip;
    }

    public List<StatusRequerimento> getLiStatusReq() {
        return liStatusReq;
    }

    public void setLiStatusReq(List<StatusRequerimento> liStatusReq) {
        this.liStatusReq = liStatusReq;
    }
    
}
