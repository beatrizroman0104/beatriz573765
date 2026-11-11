package br.fiap.beatriz573765_gs.service;

import br.fiap.beatriz573765_gs.dto.PainelItemDTO;
import br.fiap.beatriz573765_gs.repository.LeituraRepository;
import br.fiap.beatriz573765_gs.repository.TrechoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PainelService {
    private final TrechoRepository trechoRepository;
    private final LeituraRepository leituraRepository;

    public PainelService(TrechoRepository trechoRepository, LeituraRepository leituraRepository) {
        this.trechoRepository = trechoRepository;
        this.leituraRepository = leituraRepository;
    }

    public List<PainelItemDTO> montarPainel(Long bairroId){
        return trechoRepository.findByBairroIdOrderByLogradouroAsc(bairroId).stream().map(trecho -> {
            var leitura = leituraRepository.findFirstByTrechoIdOrderByDataHoraDesc(trecho.getId()).orElse(null);

            Integer ultimaLamina = leitura == null ? null : leitura.getLaminaCm();
            String ultimoStatus = leitura == null ? null : leitura.getStatus();
            return new PainelItemDTO(trecho.getLogradouro(),
                    trecho.getTipoCalcada(),
                    trecho.getAltitude(),
                    ultimaLamina,
                    ultimoStatus);
        }).toList();
    }
}
