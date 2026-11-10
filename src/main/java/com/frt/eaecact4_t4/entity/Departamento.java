package com.frt.eaecact4_t4.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "departamentos")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Departamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 100)
    private String nombre;

    @Column(nullable = false, length = 150)
    private String ubicacion;

    @OneToMany(mappedBy = "departamento", fetch = FetchType.LAZY)
    @Builder.Default
    private List<Empleado> empleados = new ArrayList<>();
}

