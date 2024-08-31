package com.system.pma.model;

import jakarta.persistence.*;
import lombok.Data;
import java.util.List;

@Entity
@Table(name = "tareas")
@Data
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "titulo", nullable = false)
    private String title;

    @Column(name = "descripcion", nullable = false)
    private String description;

    @ManyToOne
    @JoinColumn(name = "empleado_id", nullable = false)
    private Employee employee;

    @OneToMany(mappedBy = "task", cascade = CascadeType.ALL)
    private List<WorkReport> workReports;

    @Column(name = "estado", nullable = false)
    private String status; // Puede ser "Pendiente", "En Progreso", "Completada"
}