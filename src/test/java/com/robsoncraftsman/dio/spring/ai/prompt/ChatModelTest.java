package com.robsoncraftsman.dio.spring.ai.prompt;

import com.robsoncraftsman.dio.spring.ai.TestcontainersConfiguration;
import org.junit.jupiter.api.Test;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.not;

@Import(TestcontainersConfiguration.class)
@SpringBootTest
class ChatModelTest {

    @Autowired
    private ChatModel chatModel;

    @Test
    void deve_receber_resposta_correta_ao_chamar_chat_model() {
        var query = "Qual o ano de criação da linguagem de programação Java?";
        var promptTemplate = new PromptTemplate("{query}");
        var promptMessage = promptTemplate.createMessage(Map.of("query", query));
        var prompt = new Prompt(promptMessage);

        var result = chatModel.call(prompt).getResult();

        assertThat(result).isNotNull();
        var output = result.getOutput();
        assertThat(output).isNotNull();
        var text = output.getText();
        assertThat(text).contains("1995");
        System.out.println(text);
    }
}