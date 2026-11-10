package com.frt.eaecact4_t4.service;

import com.frt.eaecact4_t4.dto.AuthResponse;
import com.frt.eaecact4_t4.dto.LoginRequest;
import com.frt.eaecact4_t4.dto.RegisterRequest;
import com.frt.eaecact4_t4.dto.UsuarioResponse;
import com.frt.eaecact4_t4.entity.Role;
import com.frt.eaecact4_t4.entity.Usuario;
import com.frt.eaecact4_t4.exception.ConflictException;
import com.frt.eaecact4_t4.repository.UsuarioRepository;
import com.frt.eaecact4_t4.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    @Transactional
    public UsuarioResponse register(RegisterRequest request) {
        String username = request.username().trim();
        String email = request.email().trim().toLowerCase();

        if (usuarioRepository.existsByUsername(username)) {
            throw new ConflictException("El username '" + username + "' ya esta registrado");
        }
        if (usuarioRepository.existsByEmail(email)) {
            throw new ConflictException("El email '" + email + "' ya esta registrado");
        }

        Usuario usuario = Usuario.builder()
                .username(username)
                .email(email)
                .password(passwordEncoder.encode(request.password()))
                .role(Role.USER)
                .build();

        Usuario saved = usuarioRepository.save(usuario);
        return new UsuarioResponse(saved.getId(), saved.getUsername(), saved.getEmail(), saved.getRole().name());
    }

    public AuthResponse login(LoginRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.username().trim(), request.password()));

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return new AuthResponse(jwtService.generateToken(userDetails));
    }
}
