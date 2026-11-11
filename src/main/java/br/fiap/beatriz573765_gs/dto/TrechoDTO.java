package br.fiap.beatriz573765_gs.dto;

import jakarta.validation.constraints.*;

public record TrechoDTO(Long id, @NotBlank @Size(max = 120) String logradouro, @NotBlank @Size(max = 20) String tipoCalcada, @NotNull @Min(0) @Max(2000) Integer altitude, @NotNull Long bairroId, String bairroNome) {
}
