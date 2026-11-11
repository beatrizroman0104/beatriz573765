package br.fiap.beatriz573765_gs.mapper;

import br.fiap.beatriz573765_gs.domain.Leitura;
import br.fiap.beatriz573765_gs.domain.Trecho;
import br.fiap.beatriz573765_gs.dto.LeituraDTO;
import org.springframework.stereotype.Component;

@Component
public class LeituraMapper {
    public Leitura toEntity(LeituraDTO d, Trecho t,String status){
        return new Leitura(d.id(),d.dataHora(), d.laminaCm(), status, t);
    }
    public LeituraDTO toDTO(Leitura e){
        return new LeituraDTO(e.getId(), e.getTrecho().getId(), e.getTrecho().getLogradouro() + " ("+e.getTrecho().getBairro().getNome()+") ", e.getDataHora(), e.getLaminaCm(), e.getStatus());
    }
}
