package com.system.pma.model;

import jakarta.persistence.*;
import lombok.Data;
import java.util.List;

@Entity
@Table(name = "reportes_trabajo")
@Data
public class WorkReport {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "descripcion", nullable = false)
    private String description; // Descripción del trabajo realizado

    @ManyToOne
    @JoinColumn(name = "tarea_id", nullable = false)
    private Task task;

    @ManyToOne
    @JoinColumn(name = "empleado_id", nullable = false)
    private Employee employee;

    @Column(name = "horas_trabajadas", nullable = false)
    private int hoursWorked;
}