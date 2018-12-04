/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.femass.ds1.requerimentosfemass.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;

/**
 *
 * @author Gabriel
 */
@Entity
public class Movimentacao implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date dataMovimentacao;
    private String descricao;
    
    @ManyToOne
    private Requerimento requerimento;
    
    @ManyToOne
    private Responsavel responsavelAtual; // Responsável "de" "para"
    
    @ManyToOne
    private Responsavel responsavelSeguinte; // Responsável "de" "para"    
    
    @Enumerated(EnumType.STRING)
    private StatusMovimentacao statusMovimentacao;

    public Requerimento getRequerimento() {
        return requerimento;
    }

    public void setRequerimento(Requerimento requerimento) {
        this.requerimento = requerimento;
    }

    public Responsavel getResponsavelAtual() {
        return responsavelAtual;
    }

    public void setResponsavelAtual(Responsavel responsavelAtual) {
        this.responsavelAtual = responsavelAtual;
    }

    public Responsavel getResponsavelSeguinte() {
        return responsavelSeguinte;
    }

    public void setResponsavelSeguinte(Responsavel responsavelSeguinte) {
        this.responsavelSeguinte = responsavelSeguinte;
    }

    public StatusMovimentacao getStatusMovimentacao() {
        return statusMovimentacao;
    }

    public void setStatusMovimentacao(StatusMovimentacao statusMovimentacao) {
        this.statusMovimentacao = statusMovimentacao;
    }

    public Date getDataMovimentacao() {
        return dataMovimentacao;
    }

    public void setDataMovimentacao(Date dataMovimentacao) {
        this.dataMovimentacao = dataMovimentacao;
    }
    

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Movimentacao)) {
            return false;
        }
        Movimentacao other = (Movimentacao) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.femass.ds1.requerimentosfemass.model.Movimentacao[ id=" + id + " ]";
    }
    
}
