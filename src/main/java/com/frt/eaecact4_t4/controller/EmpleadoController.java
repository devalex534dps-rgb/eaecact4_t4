package com.frt.eaecact4_t4.controller;

import com.frt.eaecact4_t4.dto.EmpleadoRequest;
import com.frt.eaecact4_t4.dto.EmpleadoResponse;
import com.frt.eaecact4_t4.dto.PageResponse;
import com.frt.eaecact4_t4.service.EmpleadoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/empleados")
@RequiredArgsConstructor
public class EmpleadoController {

    private final EmpleadoService empleadoService;

    /**
     * Listado paginado. Ejemplo: GET /api/empleados?page=0&size=10&sort=nombre,asc
     */
    @GetMapping
    public ResponseEntity<PageResponse<EmpleadoResponse>> findAll(
            @PageableDefault(size = 10, sort = "id", direction = Sort.Direction.ASC) Pageable pageable) {
        return ResponseEntity.ok(empleadoService.findAll(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmpleadoResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(empleadoService.findById(id));
    }

    @PostMapping
    public ResponseEntity<EmpleadoResponse> create(@Valid @RequestBody EmpleadoRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(empleadoService.create(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<EmpleadoResponse> update(@PathVariable Long id,
                                                   @Valid @RequestBody EmpleadoRequest request) {
        return ResponseEntity.ok(empleadoService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        empleadoService.delete(id);
        return ResponseEntity.noContent().build();
    }
}

