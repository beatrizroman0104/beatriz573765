package br.fiap.beatriz573765_gs.repository;

import br.fiap.beatriz573765_gs.domain.Bairro;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BairroRepository extends JpaRepository<Bairro, Long> {
    List<Bairro> findAllByOrderByNomeAsc();
}
