package com.robsoncraftsman.dio.spring.ai.despesa.application;

import com.robsoncraftsman.dio.spring.ai.despesa.domain.Categoria;
import com.robsoncraftsman.dio.spring.ai.despesa.domain.Despesa;
import com.robsoncraftsman.dio.spring.ai.despesa.domain.DespesaRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ConsultarTodasDespesasUseCaseTest {

    @Mock
    private DespesaRepository despesaRepository;
    @InjectMocks
    private ConsultarTodasDespesasUseCase consultarTodasDespesasUseCase;

    @Test
    void deve_consulta_todas_despesas() {
        var despesaMocked = new Despesa();
        despesaMocked.setId(UUID.randomUUID());
        despesaMocked.setLocal("Almoço no restaurante da Maria");
        despesaMocked.setValor(BigDecimal.valueOf(111.99));
        despesaMocked.setCategoria(Categoria.ALIMENTACAO);
        var despesasMocked = List.of(despesaMocked);
        when(despesaRepository.findAll()).thenReturn(despesasMocked);

        var despesasRetornadas = consultarTodasDespesasUseCase.execute();

        assertThat(despesasRetornadas).hasSize(1);
        var despesaRetornada = despesasRetornadas.getFirst();
        assertThat(despesaRetornada.getId()).isEqualTo(despesaMocked.getId());
        assertThat(despesaRetornada.getLocal()).isEqualTo(despesaMocked.getLocal());
        assertThat(despesaRetornada.getValor()).isEqualByComparingTo(despesaMocked.getValor());
        assertThat(despesaRetornada.getCategoria()).isEqualTo(despesaMocked.getCategoria());
    }


}