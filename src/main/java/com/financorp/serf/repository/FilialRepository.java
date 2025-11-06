package com.financorp.serf.repository;

import com.financorp.serf.model.Filial;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FilialRepository extends JpaRepository<Filial, Long> {
    Optional<Filial> findByCodigo(String codigo);
    List<Filial> findByPais(String pais);
    List<Filial> findByActivaTrue();
}
