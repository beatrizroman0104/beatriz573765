package br.fiap.beatriz573765_gs.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record LeituraDTO(Long id, @NotNull Long trechoId, String trechoDescricao, @NotNull LocalDateTime dataHora, @NotNull @Min(0) @Max(300) Integer laminaCm, String status) {
}
