package br.fiap.beatriz573765_gs.dto;

import jakarta.validation.constraints.*;

public record TrechoDTO(
        Long id,
        @NotBlank(message = "Informe o logradouro.")
        @Size(max = 120, message = "O logradouro deve ter no máximo 120 caracteres.")
        String logradouro,
        @NotBlank(message = "Selecione o tipo de calçada.")
        @Size(max = 20, message = "O tipo de calçada deve ter no máximo 20 caracteres.")
        String tipoCalcada,
        @NotNull(message = "Informe a altitude.")
        @Min(value = 0, message = "A altitude não pode ser negativa.")
        @Max(value = 2000, message = "A altitude deve ser no máximo 2000 m.")
        Integer altitude,
        @NotNull(message = "Selecione o bairro.")
        Long bairroId,
        String bairroNome) {
}
