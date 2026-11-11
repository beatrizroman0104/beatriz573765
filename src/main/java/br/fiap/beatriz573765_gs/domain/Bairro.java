package br.fiap.beatriz573765_gs.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "GS676_BAIRRO")
@Setter
@Getter
@NoArgsConstructor
public class Bairro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_BAIRRO")
    private Long id;

    @Column(name = "NOME", nullable = false, length = 80)
    private String nome;

    @Column(name = "ZONA", nullable = false, length = 80)
    private String zona;

    @Column(name = "POSSUI_PISCINAO", nullable = false)
    private Boolean possuiPiscinao;

    public Bairro(Long id, String nome, String zona, Boolean possuiPiscinao) {
        this.id = id;
        this.nome = nome;
        this.zona = zona;
        this.possuiPiscinao = possuiPiscinao;
    }
}
