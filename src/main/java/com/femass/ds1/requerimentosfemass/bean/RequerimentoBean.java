package com.femass.ds1.requerimentosfemass.bean;

import com.femass.ds1.requerimentosfemass.dao.RequerimentoDao;
import com.femass.ds1.requerimentosfemass.dao.TipoRequerimentoDao;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.omnifaces.util.Messages;
import org.primefaces.event.SelectEvent;

import com.femass.ds1.requerimentosfemass.model.Aluno;
import com.femass.ds1.requerimentosfemass.model.Requerimento;
import com.femass.ds1.requerimentosfemass.model.StatusRequerimento;
import com.femass.ds1.requerimentosfemass.model.TipoRequerimento;
import com.femass.ds1.requerimentosfemass.filter.RequerimentoFilter;
import java.util.Calendar;
import java.util.GregorianCalendar;
import javax.ejb.EJB;

@ManagedBean
@ViewScoped
public class RequerimentoBean {

    private Requerimento cadastro;
    private List<Requerimento> lista;
    private List<Requerimento> lipesq;
    private List<TipoRequerimento> listatipo;
    private int size;
    private String acao;
    private RequerimentoFilter filtro;
    
    
    
    @EJB
    private RequerimentoDao dao;
    
    @EJB
    private TipoRequerimentoDao tipoDao;
    
    
//    Método para abrir e editar
    
    public void merge(){
        try {
            if (acao.equals("salvar")) {
                dao.incluir(cadastro);
            }else{
                dao.alterar(cadastro);
            }
            carregar();
            novo();
            Messages.addGlobalInfo("Requerimento Salvo com sucesso!");
        } catch (Exception e) {
            Messages.addGlobalError(">>>> ERRO: Não foi possivel Salvar o Requerimento: " + cadastro.getNumeroProtocolo());
        }
    }
    
    /**
     * Metodo de exclusão
     */
    public void excluir(){
        try{
            dao.excluir(cadastro);
            carregar();
            Messages.addGlobalInfo("Requerimento excluído com sucesso!");
        } catch (RuntimeException e) {
            e.printStackTrace();
            Messages.addGlobalError(">>>> ERRO: Não foi possivel excluir o Requerimento: "+cadastro.getNumeroProtocolo()+" - "+ e.getMessage());
        }
    }
    
    /**
     * Metodo de abertura
     */
    public void carregar() {
        try {
            lista = dao.getRequerimentos();
            size = lista.size();
            
            listatipo = tipoDao.getTipoRequerimentos();
        } catch (RuntimeException e) {
            Messages.addGlobalError(">>>> ERRO: Não foi possível carregar os Requerimentos.");
        }

    }

    public void consultar() {
        try {

            carregar();

            Requerimento req2 = null;
            for (Requerimento req : lista) {
                if (filtro.getProtocolo().equals(req.getNumeroProtocolo())) {
                    lipesq = new ArrayList<>();
                    lipesq.add(req);
                    req2 = req;
                }
            }

            if (req2 == null) {
                Messages.addGlobalInfo("Protocolo Não encontrado.");
                return;
            }
        } catch (RuntimeException e) {
            Messages.addGlobalError("Erro ao tentar consultar um processo." + e.getMessage());
        }

    }

    public void revisar() {
        if (cadastro != null) {
            cadastro.setRevisao(true);
        }
    }

    /**
     * Metodo de selecao do data table pela barra de botoes
     *
     * @param evento
     */
    public void onRowSelect(SelectEvent evento) {
        cadastro = (Requerimento) evento.getObject();
        acao = "Editar";
    }

    /**
     * Metodo Bean novo - Cria um novo objeto.
     */
    public void novo() {
        cadastro = new Requerimento();
        listatipo = tipoDao.getTipoRequerimentos();
        acao = "Salvar";
        cadastro.setDataAbertura(new Date());
        cadastro.setStatusRequerimento(StatusRequerimento.Aberto);
    }
    
    
    public Requerimento getCadastro() {
        if (cadastro == null) {
            cadastro = new Requerimento();
        }
        return cadastro;
    }

    public void setCadastro(Requerimento cadastro) {
        this.cadastro = cadastro;
    }

    public List<Requerimento> getLista() {
        return lista;
    }

    public void setLista(List<Requerimento> lista) {
        this.lista = lista;
    }

    public List<Requerimento> getLipesq() {
        return lipesq;
    }

    public void setLipesq(List<Requerimento> lipesq) {
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

    public List<TipoRequerimento> getListatipo() {
        return listatipo;
    }

    public void setListatipo(List<TipoRequerimento> listatipo) {
        this.listatipo = listatipo;
    }

}
