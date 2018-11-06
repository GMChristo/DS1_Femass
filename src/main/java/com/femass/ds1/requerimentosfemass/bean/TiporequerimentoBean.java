package com.femass.ds1.requerimentosfemass.bean;

import com.femass.ds1.requerimentosfemass.dao.TipoRequerimentoDao;
import com.femass.ds1.requerimentosfemass.model.Documento;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.event.SelectEvent;
import com.femass.ds1.requerimentosfemass.model.TipoRequerimento;
import javax.ejb.EJB;
import org.omnifaces.util.Messages;

@ManagedBean
@ViewScoped
public class TiporequerimentoBean {

    private TipoRequerimento cadastro;
    private List<TipoRequerimento> lista;
    private List<Documento> lidoc;
    private int size;
    private String acao;

    @EJB
    TipoRequerimentoDao dao;

    /**
     * Metodo de abertura
     */
    public void carregar() {
        try {
            lista = dao.getTipoRequerimentos();
            size = lista.size();

        } catch (RuntimeException e) {
            Messages.addGlobalError(">>>> ERRO: Não foi possível carregar os tipos de Requerimento.");
        }
    }

    /**
     * Metodo de selecao do data table pela barra de botoes
     *
     * @param evento
     */
    public void onRowSelect(SelectEvent evento) {
        cadastro = (TipoRequerimento) evento.getObject();
        acao = "Editar";
    }

    /**
     * Metodo Bean novo - Cria um novo objeto.
     */
    public void novo() {
        cadastro = new TipoRequerimento();
        lidoc = new ArrayList<>();
        acao = "Salvar";
    }
    
    /**
     * Metodo salva e edita 
     */
    public void merge(){
        try{
            if(acao.equals("salvar")){
               dao.incluir(cadastro);
            }else{
                dao.alterar(cadastro);
            }
            carregar();
            novo();
            Messages.addGlobalInfo("Tipo de Requerimento Salvo com sucesso!");
        } catch (RuntimeException e) {
            e.printStackTrace();
            Messages.addGlobalError(">>>> ERRO: Não foi possivel Salvar o Tipo de Requerimento: "+cadastro.getDescricao());
        }
    }
    
    /**
     * Metodo de exclusão
     */
    public void excluir(){
        try{
            dao.excluir(cadastro);
            carregar();
            Messages.addGlobalInfo("Tipo de Requerimento excluído com sucesso!");
        } catch (RuntimeException e) {
            e.printStackTrace();
            Messages.addGlobalError(">>>> ERRO: Não foi possivel excluir o Tipo de Requerimento: "+cadastro.getNome()+" - "+ e.getMessage());
        }
    }
    
    public void fechar(){
        novo();
    }

    //gets e sets 
    
    public TipoRequerimento getCadastro() {
        if (cadastro == null) {
            cadastro = new TipoRequerimento();
        }
        return cadastro;
    }

    public void setCadastro(TipoRequerimento cadastro) {
        this.cadastro = cadastro;
    }

    public List<TipoRequerimento> getLista() {
        return lista;
    }

    public void setLista(List<TipoRequerimento> lista) {
        this.lista = lista;
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
}
