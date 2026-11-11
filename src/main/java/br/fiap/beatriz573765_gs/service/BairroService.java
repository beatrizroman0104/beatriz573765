package br.fiap.beatriz573765_gs.service;

import br.fiap.beatriz573765_gs.domain.Bairro;
import br.fiap.beatriz573765_gs.dto.BairroDTO;
import br.fiap.beatriz573765_gs.mapper.BairroMapper;
import br.fiap.beatriz573765_gs.repository.BairroRepository;
import br.fiap.beatriz573765_gs.repository.TrechoRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BairroService {
    private final BairroRepository repo;
    private final BairroMapper mapper;
    private final TrechoRepository trechoRepo;

    public BairroService(BairroRepository repo, BairroMapper mapper, TrechoRepository trechoRepo) {
        this.repo = repo;
        this.mapper = mapper;
        this.trechoRepo = trechoRepo;
    }

    public List<BairroDTO> listar(){
        return repo.findAllByOrderByNomeAsc().stream().map(mapper::toDTO).toList();
    }

    public BairroDTO buscarPorId(Long id){
        return mapper.toDTO(buscarEntidade(id));
    }

    public Bairro buscarEntidade(Long id){
        return repo.findById(id).orElseThrow(() -> new IllegalArgumentException("Bairro não encontrado."));
    }

    @Transactional
    public BairroDTO salvar(BairroDTO d){
        return mapper.toDTO(repo.save(mapper.toEntity(d)));
    }

    @Transactional
    public void excluir(Long id){
        if (trechoRepo.existsByBairroId(id))
            throw new IllegalStateException("Não é possível excluir o bairro porque ele possui trechos cadastrados.");
        repo.deleteById(id);
    }
}
