package com.robsoncraftsman.dio.spring.ai.despesa.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface DespesaRepository extends JpaRepository<Despesa, UUID> {
}
