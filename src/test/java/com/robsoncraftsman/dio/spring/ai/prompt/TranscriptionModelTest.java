package com.robsoncraftsman.dio.spring.ai.prompt;

import com.robsoncraftsman.dio.spring.ai.TestcontainersConfiguration;
import org.junit.jupiter.api.Test;
import org.springframework.ai.audio.transcription.AudioTranscriptionPrompt;
import org.springframework.ai.openai.OpenAiAudioTranscriptionModel;
import org.springframework.ai.openai.OpenAiAudioTranscriptionOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.nio.charset.Charset;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@Import(TestcontainersConfiguration.class)
@SpringBootTest
class TranscriptionModelTest {

    @Autowired
    private OpenAiAudioTranscriptionModel transcriptionModel;
    @Value("classpath:prompt/transcricao_audio_despesa.md")
    private Resource promptTranscricaoAudioDespesaResource;

    @Test
    void deve_interpretar_texto_corretamente() throws IOException {
        var prompt = promptTranscricaoAudioDespesaResource.getContentAsString(Charset.defaultCharset());
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
