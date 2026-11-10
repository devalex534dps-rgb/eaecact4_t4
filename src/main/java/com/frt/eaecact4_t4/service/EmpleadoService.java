package com.frt.eaecact4_t4.service;

import com.frt.eaecact4_t4.dto.DepartamentoResponse;
import com.frt.eaecact4_t4.dto.EmpleadoRequest;
import com.frt.eaecact4_t4.dto.EmpleadoResponse;
import com.frt.eaecact4_t4.dto.PageResponse;
import com.frt.eaecact4_t4.entity.Departamento;
import com.frt.eaecact4_t4.entity.Empleado;
import com.frt.eaecact4_t4.exception.ConflictException;
import com.frt.eaecact4_t4.exception.ResourceNotFoundException;
import com.frt.eaecact4_t4.repository.DepartamentoRepository;
import com.frt.eaecact4_t4.repository.EmpleadoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class EmpleadoService {

    private final EmpleadoRepository empleadoRepository;
    private final DepartamentoRepository departamentoRepository;

    @Transactional(readOnly = true)
    public PageResponse<EmpleadoResponse> findAll(Pageable pageable) {
        return PageResponse.from(empleadoRepository.findAll(pageable).map(this::toResponse));
    }

    @Transactional(readOnly = true)
    public EmpleadoResponse findById(Long id) {
        return toResponse(getEmpleado(id));
    }

    @Transactional
    public EmpleadoResponse create(EmpleadoRequest request) {
        String email = request.email().trim().toLowerCase();
        if (empleadoRepository.existsByEmail(email)) {
            throw new ConflictException("Ya existe un empleado con el email '" + email + "'");
        }

        Empleado empleado = Empleado.builder()
                .nombre(request.nombre().trim())
                .email(email)
                .salario(request.salario())
                .departamento(getDepartamento(request.departamentoId()))
                .build();

        return toResponse(empleadoRepository.save(empleado));
    }

    @Transactional
    public EmpleadoResponse update(Long id, EmpleadoRequest request) {
        Empleado empleado = getEmpleado(id);

        String email = request.email().trim().toLowerCase();
        if (empleadoRepository.existsByEmailAndIdNot(email, id)) {
            throw new ConflictException("Ya existe otro empleado con el email '" + email + "'");
        }

        empleado.setNombre(request.nombre().trim());
        empleado.setEmail(email);
        empleado.setSalario(request.salario());
        empleado.setDepartamento(getDepartamento(request.departamentoId()));

        return toResponse(empleadoRepository.save(empleado));
    }

    @Transactional
    public void delete(Long id) {
        Empleado empleado = getEmpleado(id);
        empleadoRepository.delete(empleado);
    }

    // ---------- helpers ----------

    private Empleado getEmpleado(Long id) {
        return empleadoRepository.findWithDepartamentoById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Empleado con id " + id + " no encontrado"));
    }

    private Departamento getDepartamento(Long id) {
        return departamentoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Departamento con id " + id + " no encontrado"));
    }

    private EmpleadoResponse toResponse(Empleado e) {
        Departamento d = e.getDepartamento();
        DepartamentoResponse dep = new DepartamentoResponse(d.getId(), d.getNombre(), d.getUbicacion());
        return new EmpleadoResponse(e.getId(), e.getNombre(), e.getEmail(), e.getSalario(), dep);
    }
}

