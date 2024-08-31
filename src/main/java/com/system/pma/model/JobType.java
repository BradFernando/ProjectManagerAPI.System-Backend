package com.system.pma.model;

import jakarta.persistence.*;
import lombok.Data;
import java.util.List;

@Entity
@Table(name = "tipos_trabajo")
@Data
public class JobType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "descripcion", nullable = false)
    private String description;

    @OneToMany(mappedBy = "jobType", cascade = CascadeType.ALL)
    private List<Employee> employees;
}