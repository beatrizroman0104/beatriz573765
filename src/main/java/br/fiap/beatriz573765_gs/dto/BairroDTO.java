package br.fiap.beatriz573765_gs.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record BairroDTO(
        Long id,
        @NotBlank(message = "Informe o nome do bairro.")
        @Size(max = 80, message = "O nome deve ter no máximo 80 caracteres.")
        String nome,
        @NotBlank(message = "Selecione a zona do bairro.")
        @Size(max = 10, message = "A zona deve ter no máximo 10 caracteres.")
        String zona,
        @NotNull(message = "Informe se o bairro possui piscinão.")
        Boolean possuiPiscinao) {
}
