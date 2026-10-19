package com.robsoncraftsman.dio.spring.ai.despesa.infrastructure.http.api;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import static org.yaml.snakeyaml.nodes.Tag.STR;

@RestController
@RequestMapping("/api/despesas")
public class DespesaController {

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public String getDespesas() {
        return """
            [{
                "id": "%s",
                "descricao": "%s",
                "valor": "%s",
                "categoria": "%s"
            }]""".formatted("956f6256-d76f-442b-ae21-2052d30ca98b", "Abastecimento", "190,00", "Carro");
    }

}
