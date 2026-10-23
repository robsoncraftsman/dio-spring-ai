package com.robsoncraftsman.dio.spring.ai.despesa.infrastructure.http.api;

import com.robsoncraftsman.dio.spring.ai.despesa.application.CadastrarDespesaPeloAudioUseCase;
import com.robsoncraftsman.dio.spring.ai.despesa.application.ConsultarTodasDespesasUseCase;
import com.robsoncraftsman.dio.spring.ai.despesa.infrastructure.http.dto.DespesaResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Tag(name = "Despesas", description = "Permite a consulta e o cadastro de despesas")
@RestController
@RequestMapping("/api/despesas")
public class DespesaController {

    private final ConsultarTodasDespesasUseCase consultarTodasDespesasUseCase;
    private final CadastrarDespesaPeloAudioUseCase cadastrarDespesaPeloAudioUseCase;

    public DespesaController(ConsultarTodasDespesasUseCase consultarTodasDespesasUseCase,
                             CadastrarDespesaPeloAudioUseCase cadastrarDespesaPeloAudioUseCase) {
        this.consultarTodasDespesasUseCase = consultarTodasDespesasUseCase;
        this.cadastrarDespesaPeloAudioUseCase = cadastrarDespesaPeloAudioUseCase;
    }

    @Operation(description = "Consulta todas as despesas")
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<DespesaResponse> getTodasDespesas() {
        var despesas = consultarTodasDespesasUseCase.execute();
        return DespesaResponse.from(despesas);
    }

    @Operation(description = "Cadastra uma despesa a partir de um áudio")
    @PostMapping(value = "/audio", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public DespesaResponse cadastrarDespesaPeloAudio(@RequestParam("audio") MultipartFile audioFile) throws IOException {
        var despesa = cadastrarDespesaPeloAudioUseCase.execute(audioFile.getResource());
        return DespesaResponse.from(despesa);
    }

}
