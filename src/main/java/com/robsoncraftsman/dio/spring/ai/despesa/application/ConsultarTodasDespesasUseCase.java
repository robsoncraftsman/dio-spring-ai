package com.robsoncraftsman.dio.spring.ai.despesa.application;

import com.robsoncraftsman.dio.spring.ai.despesa.domain.Categoria;
import com.robsoncraftsman.dio.spring.ai.despesa.domain.Despesa;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Service
public class ConsultarTodasDespesasUseCase {

    public List<Despesa> execute() {
        var despesa = new Despesa();
        despesa.setId(UUID.fromString("956f6256-d76f-442b-ae21-2052d30ca98b"));
        despesa.setDescricao("Abastecimento");
        despesa.setValor(BigDecimal.valueOf(190));
        despesa.setCategoria(Categoria.TRANSPORTE);
        return List.of(despesa);
    }

}
