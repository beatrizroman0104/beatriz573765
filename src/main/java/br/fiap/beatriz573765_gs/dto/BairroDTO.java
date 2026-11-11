package br.fiap.beatriz573765_gs.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record BairroDTO(Long id, @NotBlank @Size(max = 80) String nome, @NotBlank @Size(max = 10) String zona, @NotNull Boolean possuiPiscinao) {
}
