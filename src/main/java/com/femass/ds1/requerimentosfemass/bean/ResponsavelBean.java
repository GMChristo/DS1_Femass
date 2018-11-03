package com.femass.ds1.requerimentosfemass.bean;

import com.femass.ds1.requerimentosfemass.model.Cargo;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.event.SelectEvent;

import com.femass.ds1.requerimentosfemass.model.Responsavel;

@ManagedBean
@ViewScoped
public class ResponsavelBean {
	private Responsavel cadastro;
	private List<Responsavel> lista;
	private int size;
	private String acao;
        
        /**
	 * Metodo de abertura
	 */
	public void carregar() {
		try {
			lista = new ArrayList<>();
			Responsavel resp = new Responsavel();
			resp.setId(1L);
			resp.setNome("Douglas Carvalho");
			resp.setCpf("690.484.450-78");
			resp.setCargo(Cargo.Coordenador);
			lista.add(resp);

			resp = new Responsavel();
			resp.setId(2L);
			resp.setNome("Jardeni de Oliveira");
			resp.setCpf("413.076.080-78");
			resp.setCargo(Cargo.Coordenador);
			lista.add(resp);

			resp = new Responsavel();
			resp.setId(3L);
			resp.setNome("Edson dos Santos");
			resp.setCpf("963.723.100-58");
			resp.setCargo(Cargo.Coordenador);
			lista.add(resp);

			resp = new Responsavel();
			resp.setId(4L);
			resp.setNome("Maria Eduarda");
			resp.setCpf("536.359.470-67");
			resp.setCargo(Cargo.Secretaria);
			lista.add(resp);
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
		cadastro = (Responsavel) evento.getObject();
		acao = "Editar";
	}
	
	/**
	 * Metodo Bean novo - Cria um novo objeto.
	 */
	public void novo() {
		cadastro = new Responsavel();
		acao = "Salvar";
	}
	
	public Responsavel getCadastro() {
		if (cadastro == null) {
			cadastro = new Responsavel();
		}
		return cadastro;
	}

	public void setCadastro(Responsavel cadastro) {
		this.cadastro = cadastro;
	}

	public List<Responsavel> getLista() {
		return lista;
	}

	public void setLista(List<Responsavel> lista) {
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

