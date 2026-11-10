package com.frt.eaecact4_t4.repository;

import com.frt.eaecact4_t4.entity.Empleado;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EmpleadoRepository extends JpaRepository<Empleado, Long> {

    @EntityGraph(attributePaths = "departamento")
    Page<Empleado> findAll(Pageable pageable);

    @EntityGraph(attributePaths = "departamento")
    Optional<Empleado> findWithDepartamentoById(Long id);

    boolean existsByEmail(String email);

    boolean existsByEmailAndIdNot(String email, Long id);
}

