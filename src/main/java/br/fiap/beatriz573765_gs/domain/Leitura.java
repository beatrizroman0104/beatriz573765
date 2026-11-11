package br.fiap.beatriz573765_gs.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "GS676_LEITURA")
@Getter
@Setter
@NoArgsConstructor
public class Leitura {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_LEITURA")
    private Long id;

    @Column(name = "DATA_HORA", nullable = false)
    private LocalDateTime dataHora;

    @Column(name = "LAMINA_CM", nullable = false)
    private Integer laminaCm;

    @Column(name = "STATUS", nullable = false, length = 10)
    private String status;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "TRECHO_ID", nullable = false)
    private Trecho trecho;

    public Leitura(Long id, LocalDateTime dataHora, Integer laminaCm, String status, Trecho trecho) {
        this.id = id;
        this.dataHora = dataHora;
        this.laminaCm = laminaCm;
        this.status = status;
        this.trecho = trecho;
    }
}
