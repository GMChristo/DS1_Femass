package com.femass.ds1.requerimentosfemass.bean;

import com.femass.ds1.requerimentosfemass.dao.MovimentacaoDao;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.event.SelectEvent;

import com.femass.ds1.requerimentosfemass.model.Movimentacao;
import com.femass.ds1.requerimentosfemass.filter.RequerimentoFilter;
import java.util.Date;
import javax.ejb.EJB;
import org.omnifaces.util.Messages;

@ManagedBean
@ViewScoped
public class MovimentacaoBean {

    private Movimentacao cadastro;
    private List<Movimentacao> lista;
    private List<Movimentacao> lipesq;
    private int size;
    private String acao;
    private RequerimentoFilter filtro;

    @EJB
    MovimentacaoDao dao;

    /**
     * Metodo de abertura
     */
    public void carregar() {
        try {
            lista = dao.getMovAbertas();
            size = lista.size();

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

    public void receber() {
        try {
            if (cadastro.getDataRecebimento() != null) {
                Messages.addGlobalError(">>>> ERRO: esta movimentação já foi recebida.");
                return;
            } else {
                cadastro.setDataRecebimento(new Date());
                dao.alterar(cadastro);
                cadastro = new Movimentacao();
                Messages.addGlobalInfo("Movimentação recebida com sucesso!");
            }

        } catch (RuntimeException e) {
            e.printStackTrace();
            Messages.addGlobalError(">>>> ERRO: Não foi possivel registrar o recebimento da movimentação" + e.getMessage());
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

}
