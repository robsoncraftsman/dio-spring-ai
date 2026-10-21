package com.robsoncraftsman.dio.spring.ai.prompt;

import com.robsoncraftsman.dio.spring.ai.TestcontainersConfiguration;
import org.junit.jupiter.api.Test;
import org.springframework.ai.audio.transcription.AudioTranscriptionPrompt;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.openai.OpenAiAudioTranscriptionModel;
import org.springframework.ai.openai.OpenAiAudioTranscriptionOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@Import(TestcontainersConfiguration.class)
@SpringBootTest
class StructuredOutputTest {

    @Autowired
    private OpenAiAudioTranscriptionModel transcriptionModel;
    @Autowired
    private ChatClient.Builder chatClientBuilder;
    @Value("classpath:prompt/transcricao_audio_despesa.md")
    private Resource promptTranscricaoAudioDespesaResource;
    @Value("classpath:prompt/processamento_texto_despesa.md")
    private Resource promptProcessamentoTextoDespesaResource;

    @Test
    void deve_interpretar_texto_corretamente() throws IOException {
        var transcriptionPrompt = promptTranscricaoAudioDespesaResource.getContentAsString(StandardCharsets.UTF_8);
        var audio = new ClassPathResource("audio/posto_gasolina.mp3");
        var transcriptionOptions = OpenAiAudioTranscriptionOptions.builder().prompt(transcriptionPrompt).build();
        var audioTranscriptionPrompt = new AudioTranscriptionPrompt(audio, transcriptionOptions);

        var response = transcriptionModel
                .call(audioTranscriptionPrompt);

        var output = response.getResult().getOutput();
        assertThat(output).contains("Gastei duzentos reais no posto de gasolina");
        System.out.println(output);

        var systemPrompt = promptProcessamentoTextoDespesaResource.getContentAsString(StandardCharsets.UTF_8);
        var chatClient = chatClientBuilder
                .defaultSystem(systemPrompt)
                .build();
        var despesa = chatClient.prompt()
                .user(output)
                .call()
                .entity(Despesa.class);
        System.out.println(despesa);

        assertThat(despesa).isNotNull();
        assertThat(despesa.local).isEqualToIgnoringCase("Posto de Gasolina");
        assertThat(despesa.valor).isEqualTo(200.0);
        assertThat(despesa.categoria).isEqualToIgnoringCase("Transporte");
    }

    record Despesa(String local, Double valor, String categoria) {
    }

}


