package com.robsoncraftsman.dio.spring.ai.despesa.infrastructure.http.api;

import com.robsoncraftsman.dio.spring.ai.despesa.application.CadastrarDespesaPeloAudioUseCase;
import com.robsoncraftsman.dio.spring.ai.despesa.application.ConsultarTodasDespesasUseCase;
import com.robsoncraftsman.dio.spring.ai.despesa.infrastructure.http.dto.DespesaResponse;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

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

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<DespesaResponse> getTodasDespesas() {
        var despesas = consultarTodasDespesasUseCase.execute();
        return DespesaResponse.from(despesas);
    }

    @PostMapping(value = "/audio")
    public DespesaResponse cadastrarDespesaPeloAudio(@RequestParam("audio") MultipartFile audioFile) throws IOException {
        var despesa = cadastrarDespesaPeloAudioUseCase.execute(audioFile.getResource());
        return DespesaResponse.from(despesa);
    }

}
