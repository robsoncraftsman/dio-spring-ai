package com.robsoncraftsman.dio.spring.ai.despesa.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;

@Entity
public class Despesa {

    @Id
    @GeneratedValue
    private UUID id;
    private String local;
    private BigDecimal valor;
    private Categoria categoria;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getLocal() {
        return local;
    }

    public void setLocal(String descricao) {
        this.local = descricao;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Despesa despesa)) return false;
        return Objects.equals(id, despesa.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Despesa{" +
                "id=" + id +
                ", local='" + local + '\'' +
                ", valor=" + valor +
                ", categoria=" + categoria +
                '}';
    }
}
