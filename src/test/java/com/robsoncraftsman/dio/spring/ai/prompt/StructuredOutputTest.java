package com.robsoncraftsman.dio.spring.ai.prompt;

import com.robsoncraftsman.dio.spring.ai.TestcontainersConfiguration;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.ai.audio.transcription.AudioTranscriptionPrompt;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.openai.OpenAiAudioTranscriptionModel;
import org.springframework.ai.openai.OpenAiAudioTranscriptionOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.core.io.ClassPathResource;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@Import(TestcontainersConfiguration.class)
@SpringBootTest
class StructuredOutputTest {

    @Autowired
    private OpenAiAudioTranscriptionModel transcriptionModel;
    @Autowired
    private ChatClient.Builder chatClientBuilder;

    @Test
    void deve_interpretar_texto_corretamente() {
        var transcriptionPrompt = """ 
                  Áudio em português brasileiro.
                  
                  Áudio contém descrição de gastos financeiros.
                  
                  As frases geralmente contêm:
                   - Uma ação (gastei, paguei, comprei);
                   - Um valor em reais (número + "reais");
                   - Um local ou estabelecimento (mercado, farmácia, restaurante, loja, posto de gasolina etc.);
                """;

        var audio = new ClassPathResource("audio/posto_gasolina.mp3");
        var transcriptionOptions = OpenAiAudioTranscriptionOptions.builder().prompt(transcriptionPrompt).build();
        var audioTranscriptionPrompt = new AudioTranscriptionPrompt(audio, transcriptionOptions);

        var response = transcriptionModel
                .call(audioTranscriptionPrompt);

        var output = response.getResult().getOutput();
        assertThat(output).contains("Gastei duzentos reais no posto de gasolina");
        System.out.println(output);

        var systemPrompt = """
                Você é interpretador de texto.
                Você irá receber como entrada um texto referente a um pagamento de uma despesa financeira.
                
                A frase geralmente contêm:
                   - Uma ação (gastei, paguei, comprei);
                   - Um valor em reais (número + "reais");
                   - Um local ou estabelecimento (mercado, farmácia, restaurante, loja, posto de gasolina etc.);
                   
                Extraia do texto o valor e o local da despesa.
                Categorize o tipo de despesas conforme uma das seguintes opções: Moradia, Alimentação, Transporte,
                Saúde, Educação, Lazer, Outros.
                """;
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


