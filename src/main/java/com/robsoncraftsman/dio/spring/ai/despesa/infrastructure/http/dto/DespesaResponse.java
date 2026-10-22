package com.robsoncraftsman.dio.spring.ai.despesa.infrastructure.http.dto;

import com.robsoncraftsman.dio.spring.ai.despesa.domain.Categoria;
import com.robsoncraftsman.dio.spring.ai.despesa.domain.Despesa;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public record DespesaResponse(UUID id, String local, BigDecimal valor, Categoria categoria) {

    public static DespesaResponse from(Despesa despesa) {
        return new DespesaResponse(despesa.getId(), despesa.getLocal(), despesa.getValor(), despesa.getCategoria());
    }

    public static List<DespesaResponse> from(List<Despesa> despesas) {
        return despesas.stream().map(DespesaResponse::from).toList();
    }

}
