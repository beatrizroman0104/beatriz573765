package br.fiap.beatriz573765_gs.mapper;

import br.fiap.beatriz573765_gs.domain.Bairro;
import br.fiap.beatriz573765_gs.dto.BairroDTO;
import org.springframework.stereotype.Component;

@Component
public class BairroMapper {
    public Bairro toEntity(BairroDTO d){
        return new Bairro(d.id(), d.nome(), d.zona(), d.possuiPiscinao());
    }

    public BairroDTO toDTO(Bairro e){
        return new BairroDTO(e.getId(), e.getNome(), e.getZona(), e.getPossuiPiscinao());
    }
}
