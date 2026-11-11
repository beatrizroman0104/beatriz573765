package br.fiap.beatriz573765_gs.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "GS676_TRECHO")
@Getter
@Setter
@NoArgsConstructor
public class Trecho {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_TRECHO")
    private Long id;

    @Column(name = "LOGRADOURO", nullable = false, length = 120)
    private String logradouro;

    @Column(name = "TIPO_CALCADA", nullable = false, length = 20)
    private String tipoCalcada;

    @Column(name = "ALTITUDE", nullable = false)
    private Integer altitude;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "BAIRRO_ID", nullable = false)
    private Bairro bairro;

    public Trecho(Long id, String logradouro, String tipoCalcada, Integer altitude, Bairro bairro) {
        this.id = id;
        this.logradouro = logradouro;
        this.tipoCalcada = tipoCalcada;
        this.altitude = altitude;
        this.bairro = bairro;
    }
}
