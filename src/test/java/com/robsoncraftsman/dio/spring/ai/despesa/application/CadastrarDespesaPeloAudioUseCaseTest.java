package com.robsoncraftsman.dio.spring.ai.despesa.application;

import com.robsoncraftsman.dio.spring.ai.TestcontainersConfiguration;
import com.robsoncraftsman.dio.spring.ai.despesa.domain.Categoria;
import com.robsoncraftsman.dio.spring.ai.despesa.domain.Despesa;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jpa.test.autoconfigure.AutoConfigureTestEntityManager;
import org.springframework.boot.jpa.test.autoconfigure.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.core.io.Resource;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

@Import(TestcontainersConfiguration.class)
@SpringBootTest
@AutoConfigureTestEntityManager
class CadastrarDespesaPeloAudioUseCaseTest {

    @Autowired
    private CadastrarDespesaPeloAudioUseCase cadastrarDespesaPeloAudioUseCase;
    @Value("classpath:audio/posto_gasolina.mp3")
    private Resource audioPostoGasolinaResource;
    @Autowired
    private TestEntityManager entityManager;

    @Test
    @Transactional
    void deve_transcrever_audio_e_cadastrar_despesa() throws IOException {
        var despesaTranscrita = cadastrarDespesaPeloAudioUseCase.execute(audioPostoGasolinaResource);

        assertThat(despesaTranscrita.getId()).isNotNull();
        assertThat(despesaTranscrita.getLocal()).isEqualToIgnoringCase("Posto de Gasolina");
        assertThat(despesaTranscrita.getValor()).isEqualByComparingTo(BigDecimal.valueOf(200));
        assertThat(despesaTranscrita.getCategoria()).isEqualTo(Categoria.TRANSPORTE);

        var despesaCadastrada = entityManager.find(Despesa.class, despesaTranscrita.getId());
        assertThat(despesaCadastrada).isNotNull();
        assertThat(despesaCadastrada.getId()).isEqualTo(despesaTranscrita.getId());
        assertThat(despesaCadastrada.getLocal()).isEqualTo(despesaTranscrita.getLocal());
        assertThat(despesaCadastrada.getValor()).isEqualByComparingTo(despesaTranscrita.getValor());
        assertThat(despesaCadastrada.getCategoria()).isEqualTo(despesaTranscrita.getCategoria());
    }

}