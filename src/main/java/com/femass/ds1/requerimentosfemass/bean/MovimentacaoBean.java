package com.femass.ds1.requerimentosfemass.bean;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.event.SelectEvent;

import com.femass.ds1.requerimentosfemass.model.Aluno;
import com.femass.ds1.requerimentosfemass.model.Movimentacao;
import com.femass.ds1.requerimentosfemass.model.Requerimento;
import com.femass.ds1.requerimentosfemass.model.Responsavel;

import com.femass.ds1.requerimentosfemass.model.StatusMovimentacao;
import com.femass.ds1.requerimentosfemass.model.StatusRequerimento;
import com.femass.ds1.requerimentosfemass.model.TipoRequerimento;
import com.femass.ds1.requerimentosfemass.filter.RequerimentoFilter;
import com.femass.ds1.requerimentosfemass.model.Cargo;


@ManagedBean
@ViewScoped
public class MovimentacaoBean {
	private Movimentacao cadastro;
	private List<Movimentacao> lista;
	private List<Movimentacao> lipesq;
	private int size;
	private String acao;
	private RequerimentoFilter filtro;

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

			Requerimento req = new Requerimento();
			req.setId(1L);
			req.setNumeroProtocolo("0001/2018");
			req.setDescricao("Entrega de contrato para avaliação e assinatura.");
			req.setRevisao(false);
			req.setStatusRequerimento(StatusRequerimento.Aberto);
			req.setTipoRequerimento(treq);
			req.setAluno(aluno);
                        
			req.setDataAbertura(new Date());
			
			Requerimento req2 = new Requerimento();
			req2.setId(2L);
			req2.setNumeroProtocolo("0002/2018");
			req2.setDescricao("Em anexo seguem as comprovações das matérias concluídas em outro curso técnico para avaliação.");
			req2.setRevisao(false);
			req2.setStatusRequerimento(StatusRequerimento.Aberto);
			req2.setTipoRequerimento(treq2);
			req2.setAluno(aluno2);
			req2.setDataAbertura(new Date());
			
			Responsavel resp = new Responsavel();
			resp.setId(1L);
			resp.setNome("Douglas Carvalho");
			resp.setCpf("690.484.450-78");
			resp.setCargo(Cargo.Coordenador);
			
			lista = new ArrayList<>();
			Movimentacao mov = new Movimentacao();
			mov.setId(1L);
			mov.setDataMovimentacao(new Date());
			mov.setDescricao("Descrever alguma coisa");
			mov.setRequerimento(req);
			mov.setResponsavelAtual(resp);
			mov.setStatusMovimentacao(StatusMovimentacao.Encaminhado);
			lista.add(mov);
			
			Movimentacao mov2 = new Movimentacao();
			mov2.setId(1L);
			mov2.setDataMovimentacao(new Date());
			mov2.setDescricao("Descrever alguma coisa");
			mov2.setRequerimento(req2);
			mov2.setResponsavelAtual(resp);
			mov2.setStatusMovimentacao(StatusMovimentacao.Encaminhado);
			lista.add(mov2);
			
			size = lista.size();
			System.out.println("Tamanho da lista = " + size);

		} catch (RuntimeException e) {
			System.out.println("Erro ao tentar gerar a lista.");
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

}
