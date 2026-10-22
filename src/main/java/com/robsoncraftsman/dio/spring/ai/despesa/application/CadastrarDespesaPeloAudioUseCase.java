package com.robsoncraftsman.dio.spring.ai.despesa.application;

import com.robsoncraftsman.dio.spring.ai.despesa.domain.Categoria;
import com.robsoncraftsman.dio.spring.ai.despesa.domain.Despesa;
import com.robsoncraftsman.dio.spring.ai.despesa.domain.DespesaRepository;
import org.springframework.ai.audio.transcription.AudioTranscriptionPrompt;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.openai.OpenAiAudioTranscriptionModel;
import org.springframework.ai.openai.OpenAiAudioTranscriptionOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

@Service
public class CadastrarDespesaPeloAudioUseCase {

    private OpenAiAudioTranscriptionModel transcriptionModel;
    private ChatClient.Builder chatClientBuilder;
    private Resource promptTranscricaoAudioDespesaResource;
    private Resource promptProcessamentoTextoDespesaResource;
    private DespesaRepository despesaRepository;

    public CadastrarDespesaPeloAudioUseCase(OpenAiAudioTranscriptionModel transcriptionModel, ChatClient.Builder chatClientBuilder,
                                            @Value("classpath:prompt/transcricao_audio_despesa.md") Resource promptTranscricaoAudioDespesaResource,
                                            @Value("classpath:prompt/processamento_texto_despesa.md") Resource promptProcessamentoTextoDespesaResource, DespesaRepository despesaRepository) {
        this.transcriptionModel = transcriptionModel;
        this.chatClientBuilder = chatClientBuilder;
        this.promptTranscricaoAudioDespesaResource = promptTranscricaoAudioDespesaResource;
        this.promptProcessamentoTextoDespesaResource = promptProcessamentoTextoDespesaResource;
        this.despesaRepository = despesaRepository;
    }

    public Despesa execute(Resource audioDespesaResource) throws IOException {
        var textoDespesa = converteAudioParaTexto(audioDespesaResource);
        var despesa = converteTextoParaDespesa(textoDespesa);
        despesaRepository.save(despesa);
        return despesa;
    }

    private String converteAudioParaTexto(Resource audioDespesaResource) throws IOException {
        var transcriptionPrompt = promptTranscricaoAudioDespesaResource.getContentAsString(StandardCharsets.UTF_8);
        var transcriptionOptions = OpenAiAudioTranscriptionOptions.builder().prompt(transcriptionPrompt).build();
        var audioTranscriptionPrompt = new AudioTranscriptionPrompt(audioDespesaResource, transcriptionOptions);

        var response = transcriptionModel
                .call(audioTranscriptionPrompt);

        return response.getResult().getOutput();
    }

    private Despesa converteTextoParaDespesa(String textoDespesa) throws IOException {
        var systemPrompt = promptProcessamentoTextoDespesaResource.getContentAsString(StandardCharsets.UTF_8);
        var chatClient = chatClientBuilder
                .defaultSystem(systemPrompt)
                .build();
        var despesaConvertidaDoTexto = chatClient.prompt()
                .user(textoDespesa)
                .call()
                .entity(DespesaConvertidaDoTexto.class);
        if (despesaConvertidaDoTexto != null) {
            return despesaConvertidaDoTexto.toEntity();
        } else {
            throw new IllegalArgumentException("Não foi possível interpretar o texto da despesa");
        }
    }

    record DespesaConvertidaDoTexto(String local, Double valor, Categoria categoria) {
        public Despesa toEntity() {
            var despesa = new Despesa();
            despesa.setLocal(this.local);
            despesa.setValor(BigDecimal.valueOf(this.valor));
            despesa.setCategoria(this.categoria);
            return despesa;
        }
    }
}
