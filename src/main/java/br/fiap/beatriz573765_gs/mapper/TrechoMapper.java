package br.fiap.beatriz573765_gs.mapper;

import br.fiap.beatriz573765_gs.domain.Bairro;
import br.fiap.beatriz573765_gs.domain.Trecho;
import br.fiap.beatriz573765_gs.dto.TrechoDTO;
import org.springframework.stereotype.Component;

@Component
public class TrechoMapper {
    public Trecho toEntity(TrechoDTO d, Bairro b){
        return new Trecho(d.id(), d.logradouro(), d.tipoCalcada(), d.altitude(),b);
    }
    public TrechoDTO toDTO(Trecho e){
        return new TrechoDTO(e.getId(), e.getLogradouro(), e.getTipoCalcada(), e.getAltitude(), e.getBairro().getId(), e.getBairro().getNome());
    }
}
