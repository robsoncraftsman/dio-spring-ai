package com.robsoncraftsman.dio.spring.ai.despesa.infrastructure.http.api;

import com.robsoncraftsman.dio.spring.ai.TestcontainersConfiguration;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Import(TestcontainersConfiguration.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Sql("classpath:sql/test-data.sql")
class DespesaControllerTest {

    @Autowired
    private MockMvc mvc;

    @Test
    void deve_retornar_lista_despesas() throws Exception {
        mvc.perform(get("/api/despesas").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is("956f6256-d76f-442b-ae21-2052d30ca98b")))
                .andExpect(jsonPath("$[0].local", is("Abastecimento de gasolina")))
                .andExpect(jsonPath("$[0].valor", is(190.0)))
                .andExpect(jsonPath("$[0].categoria", is("TRANSPORTE")));
    }

}