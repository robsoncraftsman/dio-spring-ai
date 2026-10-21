package com.robsoncraftsman.dio.spring.ai.prompt;

import com.robsoncraftsman.dio.spring.ai.TestcontainersConfiguration;
import org.junit.jupiter.api.Test;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

import static org.assertj.core.api.Assertions.assertThat;

@Import(TestcontainersConfiguration.class)
@SpringBootTest
class ChatClientTest {

    @Autowired
    private ChatClient.Builder chatClientBuilder;

    @Test
    void deve_receber_resposta_correta_ao_chamar_chat_client() {
        var userInput = "Quanto é 20 mais 10";

        var chatClient = chatClientBuilder
                .defaultSystem("Você é um matemático. Apresente apenas o resultado final do cálculo.")
                .build();

        var content = chatClient.prompt()
                .user(userInput)
                .call()
                .content();

        assertThat(content).contains("30");
        System.out.println(content);
    }

}
