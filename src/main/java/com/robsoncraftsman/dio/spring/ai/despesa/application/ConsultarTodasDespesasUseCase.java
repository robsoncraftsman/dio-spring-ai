package com.robsoncraftsman.dio.spring.ai.despesa.application;

import com.robsoncraftsman.dio.spring.ai.despesa.domain.Categoria;
import com.robsoncraftsman.dio.spring.ai.despesa.domain.Despesa;
import com.robsoncraftsman.dio.spring.ai.despesa.domain.DespesaRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Service
public class ConsultarTodasDespesasUseCase {

    private final DespesaRepository despesaRepository;

    public ConsultarTodasDespesasUseCase(DespesaRepository despesaRepository) {
        this.despesaRepository = despesaRepository;
    }

    public List<Despesa> execute() {
        return despesaRepository.findAll();
    }

}
