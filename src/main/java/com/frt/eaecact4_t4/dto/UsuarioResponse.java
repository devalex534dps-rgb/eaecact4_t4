package com.frt.eaecact4_t4.dto;

/**
 * Respuesta de registro. Nunca incluye la contrasena.
 */
public record UsuarioResponse(Long id, String username, String email, String role) {
}
