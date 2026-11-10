package com.frt.eaecact4_t4.dto;

import java.math.BigDecimal;

public record EmpleadoResponse(
        Long id,
        String nombre,
        String email,
        BigDecimal salario,
        DepartamentoResponse departamento
) {
}

