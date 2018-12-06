package com.femass.ds1.requerimentosfemass.bean;

import com.femass.ds1.requerimentosfemass.dao.AlunoDao;
import com.femass.ds1.requerimentosfemass.dao.MovimentacaoDao;
import com.femass.ds1.requerimentosfemass.dao.RequerimentoDao;
import com.femass.ds1.requerimentosfemass.dao.ResponsavelDao;
import com.femass.ds1.requerimentosfemass.dao.TipoRequerimentoDao;
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
import com.femass.ds1.requerimentosfemass.model.Movimentacao;
import com.femass.ds1.requerimentosfemass.model.Responsavel;
import java.util.Arrays;
import javax.ejb.EJB;

@ManagedBean
@ViewScoped
public class RequerimentoBean {

    private Requerimento cadastro;
    private List<Requerimento> lista;
    private List<Requerimento> lipesq;
    private List<TipoRequerimento> listatipo;
    private List<StatusRequerimento> liStatusReq;
    private int size;
    private String acao;
    private RequerimentoFilter filtro;
    private Aluno aluno;
    private boolean liTodos;
    private Movimentacao movimentacao;
    private List<Responsavel> liResponsavel;

    @EJB
    RequerimentoDao dao;

    @EJB
    TipoRequerimentoDao tipoDao;

    @EJB
    AlunoDao alunoDAO;
    
    @EJB
    MovimentacaoDao movDAO;
    
    @EJB
    ResponsavelDao respDAO;

    /**
     * Metodo de abertura
     */
    public void carregar() {
        try {
            if(liTodos == true){
                lista = dao.getRequerimentos();
            }else{
                lista = dao.getRequerimentosAbertos();
            }
            size = lista.size();
            listatipo = tipoDao.getTipoRequerimentos();
            liStatusReq = Arrays.asList(StatusRequerimento.values());
        } catch (RuntimeException e) {
            Messages.addGlobalError(">>>> ERRO: Não foi possível carregar os Requerimentos." + "Erro: " + e.getMessage());
        }

    }

    /**
     * Método de Consulta
     */
    public void consultar() {
        try {
            lipesq = dao.pesqRequerimentos(filtro.getProtocolo());
        } catch (RuntimeException e) {
            Messages.addGlobalError("Erro ao tentar consultar um processo." + e.getMessage());
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
        cadastro.setNumeroProtocolo("001/2018");
        cadastro.setDataAbertura(new Date());
        cadastro.setStatusRequerimento(StatusRequerimento.Aberto);
        Aluno aluno = alunoDAO.porID(1L);
        cadastro.setAluno(aluno);
    }

    /**
     * Método Salva ou edita
     */
    public void merge() {
        try {
            if (acao.equals("Salvar")) {
                dao.incluir(cadastro);
                Messages.addGlobalInfo("Requerimento Salvo com sucesso!");
            } else {
                dao.alterar(cadastro);
                Messages.addGlobalInfo("Requerimento Editado com sucesso!");
            }
            carregar();
            fechar();

        } catch (Exception e) {
            Messages.addGlobalError(">>>> ERRO: Não foi possivel Salvar o Requerimento: " + cadastro.getNumeroProtocolo());
        }
    }

    /**
     * Metodo de exclusão alterado para cancelamento de requerimento
     */
    public void cancelar() {
        try {
            cadastro.setStatusRequerimento(StatusRequerimento.Cancelado);
            dao.alterar(cadastro);
            carregar();
            Messages.addGlobalInfo("Requerimento Cancelado com sucesso!");
        } catch (RuntimeException e) {
            e.printStackTrace();
            Messages.addGlobalError(">>>> ERRO: Não foi possivel Cancelar o Requerimento: " + cadastro.getNumeroProtocolo() + " - " + e.getMessage());
        }
    }
    
    public void atribuir(){
        try{
            liResponsavel = respDAO.getResponsaveis();
            movimentacao.setDataMovimentacao(new Date());
            movimentacao.setRequerimento(cadastro);
            Responsavel responsavel = respDAO.buscarPorCurso(cadastro.getAluno().getCurso());
            if(responsavel != null){
                movimentacao.setResponsavel(responsavel);
            }else{
                Messages.addGlobalError(">>>> ERRO: Não foi possivel Identificar o responsável do curso " + cadastro.getAluno().getCurso().getNome());
                return;
            }
            Messages.addGlobalInfo("Requerimento Atribuído com sucesso!");
        }catch(RuntimeException e){
            e.printStackTrace();
            Messages.addGlobalError(">>>> ERRO: Não foi possivel Atribuir o Requerimento: " + cadastro.getNumeroProtocolo() + " - " + e.getMessage());
        }
    }
    
    public void salvarMovimentacao(){
        try{
            if(movimentacao.getResponsavel() != null){
                movDAO.incluir(movimentacao);
                Requerimento req = movimentacao.getRequerimento();
                req.setStatusRequerimento(StatusRequerimento.Em_Análise);
                dao.alterar(req);
                
                fechar();
            }else{
                Messages.addGlobalError(">>>> ERRO: Não foi possivel registrar a movimentação! - (Não possui responsável) ");
                return;
            }
            Messages.addGlobalInfo("Movimentação registrada com sucesso!");
        }catch(RuntimeException e){
            e.printStackTrace();
            Messages.addGlobalError(">>>> ERRO: Não foi possivel registrar a movimentação do processo:" + cadastro.getNumeroProtocolo() + " - " + e.getMessage());
        }
    }

    /**
     * Método para revisão
     */
    public void revisar() {
        if (cadastro != null) {
            cadastro.setRevisao(true);
        }
    }

    /**
     * Método fechar
     */
    public void fechar() {
        cadastro = new Requerimento();
        movimentacao = new Movimentacao();
        acao = "";
    }

    public Aluno getAluno() {
        return aluno;
    }

    public void setAluno(Aluno aluno) {
        this.aluno = aluno;
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

    public List<StatusRequerimento> getLiStatusReq() {
        return liStatusReq;
    }

    public void setLiStatusReq(List<StatusRequerimento> liStatusReq) {
        this.liStatusReq = liStatusReq;
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

    public boolean isLiTodos() {
        return liTodos;
    }

    public void setLiTodos(boolean liTodos) {
        this.liTodos = liTodos;
    }

    public Movimentacao getMovimentacao() {
        if(movimentacao == null){
            movimentacao = new Movimentacao();
        }
        return movimentacao;
    }

    public void setMovimentacao(Movimentacao movimentacao) {
        this.movimentacao = movimentacao;
    }

    public List<Responsavel> getLiResponsavel() {
        return liResponsavel;
    }

    public void setLiResponsavel(List<Responsavel> liResponsavel) {
        this.liResponsavel = liResponsavel;
    }
    
}
