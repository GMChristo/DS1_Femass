package com.femass.ds1.requerimentosfemass.bean;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.event.SelectEvent;
//com.femass.ds1.requerimentosfemass
import com.femass.ds1.requerimentosfemass.model.Setor;
import com.femass.ds1.requerimentosfemass.model.TipoRequerimento;

@ManagedBean
@ViewScoped
public class TiporequerimentoBean {
	private TipoRequerimento cadastro;
	private List<TipoRequerimento> lista;
	private int size;
	private String acao;
	
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
	
	
	/**
	 * Metodo de abertura
	 */
	public void carregar() {
		try {
			lista = new ArrayList<>();
			TipoRequerimento treq = new TipoRequerimento();
			treq.setId(1L);
			treq.setNome("Assinatura do Contrato de estágio");
			treq.setDescricao("Descrição do tipo deste requerimento");
			treq.setSetor("Recepção");
			lista.add(treq);

			treq = new TipoRequerimento();
			treq.setId(2L);
			treq.setNome("Cancelamento de matrícula");
			treq.setDescricao("Descrição do tipo deste requerimento");
			treq.setSetor("Coordenação");
			lista.add(treq);

			treq = new TipoRequerimento();
			treq.setId(3L);
			treq.setNome("Certificação de Conhecimento");
			treq.setDescricao("Descrição do tipo deste requerimento");
			treq.setSetor("Coordenação");
			lista.add(treq);

			treq = new TipoRequerimento();
			treq.setId(4L);
			treq.setNome("Declaração");
			treq.setDescricao("Descrição do tipo deste requerimento");
			treq.setSetor("Coordenação");
			lista.add(treq);
			
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
		cadastro = (TipoRequerimento) evento.getObject();
		acao = "Editar";
	}
	
	/**
	 * Metodo Bean novo - Cria um novo objeto.
	 */
	public void novo() {
		cadastro = new TipoRequerimento();
		acao = "Salvar";
	}

}

