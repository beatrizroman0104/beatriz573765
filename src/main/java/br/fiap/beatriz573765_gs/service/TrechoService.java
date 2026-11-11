package br.fiap.beatriz573765_gs.service;

import br.fiap.beatriz573765_gs.domain.Trecho;
import br.fiap.beatriz573765_gs.dto.TrechoDTO;
import br.fiap.beatriz573765_gs.mapper.TrechoMapper;
import br.fiap.beatriz573765_gs.repository.LeituraRepository;
import br.fiap.beatriz573765_gs.repository.TrechoRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TrechoService {
    private final TrechoRepository repo;
    private final TrechoMapper mapper;
    private final BairroService bairroService;
    private final LeituraRepository leituraRepo;

    public TrechoService(TrechoRepository repo, TrechoMapper mapper, BairroService bairroService, LeituraRepository leituraRepo) {
        this.repo = repo;
        this.mapper = mapper;
        this.bairroService = bairroService;
        this.leituraRepo = leituraRepo;
    }

    public List<TrechoDTO> listar(){
        return repo.findAllByOrderByAltitudeAscLogradouroAsc().stream().map(mapper::toDTO).toList();
    }

    public TrechoDTO buscarPorId(Long id){
        return mapper.toDTO(buscarEntidade(id));
    }

    public Trecho buscarEntidade(Long id) {
        return repo.findById(id).orElseThrow(() -> new IllegalArgumentException("Trecho não encontrado."));
    }

    @Transactional
    public TrechoDTO salvar(TrechoDTO d){
        Trecho trecho = mapper.toEntity(d, bairroService.buscarEntidade(d.bairroId()));
        return mapper.toDTO(repo.save(trecho));
    }

    @Transactional
    public void excluir(Long id){
        if (leituraRepo.existsByTrechoId(id)) {
            throw new IllegalStateException("Não é possível excluir o trecho porque ele possui leituras cadastradas.");
        }
        repo.deleteById(id);
    }
}
