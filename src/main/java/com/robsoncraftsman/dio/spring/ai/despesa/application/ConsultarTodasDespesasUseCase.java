package com.robsoncraftsman.dio.spring.ai.despesa.application;

import com.robsoncraftsman.dio.spring.ai.despesa.domain.Despesa;
import com.robsoncraftsman.dio.spring.ai.despesa.domain.DespesaRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class ConsultarTodasDespesasUseCase {

    private final DespesaRepository despesaRepository;

    public ConsultarTodasDespesasUseCase(DespesaRepository despesaRepository) {
        this.despesaRepository = despesaRepository;
    }

    public List<Despesa> execute() {
        return despesaRepository.findAll();
    }

}
