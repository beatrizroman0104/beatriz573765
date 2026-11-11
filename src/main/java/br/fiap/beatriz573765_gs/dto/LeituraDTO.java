package br.fiap.beatriz573765_gs.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

public record LeituraDTO(
        Long id,
        @NotNull(message = "Selecione o trecho.")
        Long trechoId,
        String trechoDescricao,
        @NotNull(message = "Informe a data e a hora da leitura.")
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
        LocalDateTime dataHora,
        @NotNull(message = "Informe a lâmina de água em cm.")
        @Min(value = 0, message = "A lâmina não pode ser negativa.")
        @Max(value = 300, message = "A lâmina deve ser no máximo 300 cm.")
        Integer laminaCm,
        String status) {
}
