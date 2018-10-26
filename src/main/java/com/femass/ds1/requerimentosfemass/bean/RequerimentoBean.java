package com.femass.ds1.requerimentosfemass.bean;

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

@ManagedBean
@ViewScoped
public class RequerimentoBean {
	private Requerimento cadastro;
	private List<Requerimento> lista;
	private List<Requerimento> lipesq;
	private int size;
	private String acao;
	private RequerimentoFilter filtro;

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

	/**
	 * Metodo de abertura
	 */
	public void carregar() {
		try {
			// cria aluno
			Aluno aluno = new Aluno();
			aluno.setId(1L);
			aluno.setNome("Jocimar Galante");

			Aluno aluno2 = new Aluno();
			aluno2.setId(2L);
			aluno2.setNome("Alex Santos");

			// cria tipo do requerimento
			TipoRequerimento treq = new TipoRequerimento();
			treq.setId(1L);
			treq.setNome("Assinatura do Contrato de estágio");
			treq.setDescricao("Descrição do tipo deste requerimento");
			treq.setSetor("Recepção");

			TipoRequerimento treq2 = new TipoRequerimento();
			treq2.setId(2L);
			treq2.setNome("Dispensa de Disciplina");
			treq2.setDescricao("Descrição do tipo deste requerimento");
			treq2.setSetor("Coordenação");

			lista = new ArrayList<>();

			Requerimento req = new Requerimento();
			req.setId(1L);
			req.setNumeroProtocolo("0001/2018");
			req.setDescricao("Entrega de contrato para avaliação e assinatura.");
			req.setRevisao(false);
			req.setStatusRequerimento(StatusRequerimento.Aberto);
			req.setTipoRequerimento(treq);
			req.setAluno(aluno);
			req.setDataAbertura(new Date());
			lista.add(req);

			Requerimento req2 = new Requerimento();
			req2.setId(2L);
			req2.setNumeroProtocolo("0002/2018");
			req2.setDescricao("Em anexo seguem as comprovações das matérias concluídas em outro curso técnico para avaliação.");
			req2.setRevisao(false);
			req2.setStatusRequerimento(StatusRequerimento.Aberto);
			req2.setTipoRequerimento(treq2);
			req2.setAluno(aluno2);
			req2.setDataAbertura(new Date());
			lista.add(req2);

			size = lista.size();
			System.out.println("Tamanho da lista = " + size);

		} catch (RuntimeException e) {
			System.out.println("Erro ao tentar gerar a lista.");
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
	
	
	public void revisar(){
		if(cadastro != null){
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
		acao = "Salvar";
		cadastro.setDataAbertura(new Date());
		cadastro.setStatusRequerimento(StatusRequerimento.Aberto);
	}

}
