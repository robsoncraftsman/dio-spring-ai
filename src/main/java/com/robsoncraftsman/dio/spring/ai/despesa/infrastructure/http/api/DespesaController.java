package com.robsoncraftsman.dio.spring.ai.despesa.infrastructure.http.api;

import com.robsoncraftsman.dio.spring.ai.despesa.application.ConsultarTodasDespesasUseCase;
import com.robsoncraftsman.dio.spring.ai.despesa.infrastructure.http.dto.DespesaResponse;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/despesas")
public class DespesaController {

    private final ConsultarTodasDespesasUseCase consultarTodasDespesasUseCase;

    public DespesaController(ConsultarTodasDespesasUseCase consultarTodasDespesasUseCase) {
        this.consultarTodasDespesasUseCase = consultarTodasDespesasUseCase;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<DespesaResponse> getTodasDespesas() {
        var despesas = consultarTodasDespesasUseCase.execute();
        return DespesaResponse.from(despesas);
    }

}
