package com.robsoncraftsman.dio.spring.ai.prompt;

import com.robsoncraftsman.dio.spring.ai.TestcontainersConfiguration;
import org.junit.jupiter.api.Test;
import org.springframework.ai.audio.tts.TextToSpeechPrompt;
import org.springframework.ai.openai.OpenAiAudioSpeechModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

import java.io.IOException;
import java.nio.file.Files;

import static org.assertj.core.api.Assertions.assertThat;

@Import(TestcontainersConfiguration.class)
@SpringBootTest
class SpeechModelTest {

    @Autowired
    private OpenAiAudioSpeechModel speechModel;

    @Test
    void deve_transcrever_texto_em_audio() throws IOException {
        var text = "Gastei 200 reais no posto de gasolina";
        var speechPrompt = new TextToSpeechPrompt(text);

        var response = speechModel.call(speechPrompt);
        assertThat(response).isNotNull();

        byte[] output = response.getResult().getOutput();
        assertThat(output).hasSizeGreaterThan(1024);

        var tempFile = Files.createTempFile("AUDIO_", ".mp3");
        Files.write(tempFile, output);
        System.out.println(tempFile.toAbsolutePath());
    }

}
