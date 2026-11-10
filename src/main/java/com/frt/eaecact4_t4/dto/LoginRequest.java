package com.frt.eaecact4_t4.dto;

import jakarta.validation.constraints.NotBlank;

public record LoginRequest(

                @NotBlank(message = "El username es obligatorio") String username,

                @NotBlank(message = "La contrasena es obligatoria") String password) {
}
