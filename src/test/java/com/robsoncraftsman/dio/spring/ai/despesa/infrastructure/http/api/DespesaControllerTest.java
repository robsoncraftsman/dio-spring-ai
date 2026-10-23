package com.robsoncraftsman.dio.spring.ai.despesa.infrastructure.http.api;

import com.jayway.jsonpath.JsonPath;
import com.robsoncraftsman.dio.spring.ai.TestcontainersConfiguration;
import com.robsoncraftsman.dio.spring.ai.despesa.domain.Categoria;
import com.robsoncraftsman.dio.spring.ai.despesa.domain.Despesa;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jpa.test.autoconfigure.AutoConfigureTestEntityManager;
import org.springframework.boot.jpa.test.autoconfigure.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.context.annotation.Import;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Import(TestcontainersConfiguration.class)
@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestEntityManager
class DespesaControllerTest {

    @Autowired
    private MockMvc mvc;
    @Value("classpath:audio/posto_gasolina.mp3")
    private Resource postoGasolinaAudioResource;
    @Autowired
    private TestEntityManager entityManager;

    @Test
    @Transactional
    void deve_retornar_lista_despesas() throws Exception {
        var despesa = new Despesa();
        despesa.setLocal("posto de gasolina");
        despesa.setValor(BigDecimal.valueOf(200));
        despesa.setCategoria(Categoria.TRANSPORTE);
        var despesaCadastrada = entityManager.merge(despesa);

        mvc.perform(get("/api/despesas").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is(despesaCadastrada.getId().toString())))
                .andExpect(jsonPath("$[0].local", is(despesaCadastrada.getLocal())))
                .andExpect(jsonPath("$[0].valor", is(despesaCadastrada.getValor().intValue())))
                .andExpect(jsonPath("$[0].categoria", is(despesaCadastrada.getCategoria().name())));
    }

    @Test
    @Transactional
    void deve_cadastrar_despesa_pelo_audio() throws Exception {
        var postoGasolinaAudioFile = new MockMultipartFile("audio",
                "audio.mp3",
                "audio/mpeg",
                postoGasolinaAudioResource.getInputStream());

        var jsonResult = mvc.perform(multipart("/api/despesas/audio").file(postoGasolinaAudioFile))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(notNullValue())))
                .andExpect(jsonPath("$.local", is("posto de gasolina")))
                .andExpect(jsonPath("$.valor", is(200.0)))
                .andExpect(jsonPath("$.categoria", is("TRANSPORTE")))
                .andReturn();

        var json = jsonResult.getResponse().getContentAsString();
        String id = JsonPath.read(json, "$.id");
        var uuid = UUID.fromString(id);
        var despesaCadastrada = entityManager.find(Despesa.class, uuid);
        assertThat(despesaCadastrada).isNotNull();
        assertThat(despesaCadastrada.getId()).isEqualTo(uuid);
        assertThat(despesaCadastrada.getLocal()).isEqualTo("posto de gasolina");
        assertThat(despesaCadastrada.getValor()).isEqualByComparingTo(BigDecimal.valueOf(200));
        assertThat(despesaCadastrada.getCategoria()).isEqualTo(Categoria.TRANSPORTE);
    }

}