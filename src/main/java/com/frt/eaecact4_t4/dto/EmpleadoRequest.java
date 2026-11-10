package com.frt.eaecact4_t4.dto;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public record EmpleadoRequest(

        @NotBlank(message = "El nombre es obligatorio")
        @Size(min = 2, max = 100, message = "El nombre debe tener entre 2 y 100 caracteres")
        String nombre,

        @NotBlank(message = "El email es obligatorio")
        @Email(message = "El email no tiene un formato valido")
        @Size(max = 150, message = "El email no puede superar 150 caracteres")
        String email,

        @NotNull(message = "El salario es obligatorio")
        @DecimalMin(value = "0.0", inclusive = false, message = "El salario debe ser mayor que 0")
        @Digits(integer = 10, fraction = 2, message = "El salario admite maximo 10 enteros y 2 decimales")
        BigDecimal salario,

        @NotNull(message = "El departamentoId es obligatorio")
        Long departamentoId
) {
}
