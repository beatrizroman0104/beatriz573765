package br.fiap.beatriz573765_gs.service;

import br.fiap.beatriz573765_gs.domain.Leitura;
import br.fiap.beatriz573765_gs.domain.Trecho;
import br.fiap.beatriz573765_gs.dto.LeituraDTO;
import br.fiap.beatriz573765_gs.mapper.LeituraMapper;
import br.fiap.beatriz573765_gs.repository.LeituraRepository;
import br.fiap.beatriz573765_gs.service.TrechoService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LeituraService {
    private final LeituraRepository repo;
    private final LeituraMapper mapper;
    private final TrechoService trechoService;
    private static final int LIMIAR_ALERTA=10;

    public LeituraService(LeituraRepository repo, LeituraMapper mapper, TrechoService trechoService) {
        this.repo = repo;
        this.mapper = mapper;
        this.trechoService = trechoService;
    }

    public List<LeituraDTO> listar(){
        return repo.findAllByOrderByDataHoraDesc().stream().map(mapper::toDTO).toList();
    }

    public LeituraDTO buscarPorId(Long id){
        return mapper.toDTO(buscarEntidade(id));
    }

    private Leitura buscarEntidade(Long id) {
        return repo.findById(id).orElseThrow(()->new IllegalArgumentException("Leitura não encontrada."));
    }

    public String calcularStatus(int laminaCm){
        if (laminaCm>=LIMIAR_ALERTA){
            return "ALERTA";
        }
        else if (laminaCm>=LIMIAR_ALERTA/2){
            return "ATENCAO";
        }
        else return "NORMAL";
    }

    @Transactional
    public LeituraDTO salvar(LeituraDTO d){
        if (d.dataHora().isAfter(java.time.LocalDateTime.now())){
            throw  new IllegalArgumentException("A data e hora não podem estar no futuro.");
        }
        Trecho trecho = trechoService.buscarEntidade(d.trechoId());
        return mapper.toDTO(repo.save(mapper.toEntity(d,trecho,calcularStatus(d.laminaCm()))));
    }

    @Transactional
    public void excluir(Long id){
        repo.deleteById(id);
    }
}
