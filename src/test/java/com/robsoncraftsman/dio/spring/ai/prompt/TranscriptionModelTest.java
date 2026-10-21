package com.robsoncraftsman.dio.spring.ai.prompt;

import com.robsoncraftsman.dio.spring.ai.TestcontainersConfiguration;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.ai.audio.transcription.AudioTranscriptionPrompt;
import org.springframework.ai.openai.OpenAiAudioTranscriptionModel;
import org.springframework.ai.openai.OpenAiAudioTranscriptionOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.core.io.ClassPathResource;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@Import(TestcontainersConfiguration.class)
@SpringBootTest
class TranscriptionModelTest {

    @Autowired
    private OpenAiAudioTranscriptionModel transcriptionModel;

    @Test
    void deve_interpretar_texto_corretamente() {
        var prompt = """ 
                  Áudio em português brasileiro.
                  Áudio contém descrição de gastos financeiros.
                  As frases geralmente contêm:
                   - Uma ação (gastei, paguei, comprei);
                   - Um valor em reais (número + "reais");
                   - Um local ou estabelecimento (mercado, farmácia, restaurante, loja, posto de gasolina etc.);
                """;

        var audio = new ClassPathResource("audio/posto_gasolina.mp3");
        var transcriptionOptions = OpenAiAudioTranscriptionOptions.builder().prompt(prompt).build();
        var audioTranscriptionPrompt = new AudioTranscriptionPrompt(audio, transcriptionOptions);

        var response = transcriptionModel
                .call(audioTranscriptionPrompt);

        var output = response.getResult().getOutput();
        assertThat(output).contains("Gastei duzentos reais no posto de gasolina");

        System.out.println(output);
    }
}
