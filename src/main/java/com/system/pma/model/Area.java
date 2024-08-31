package com.system.pma.model;

import jakarta.persistence.*;
import lombok.Data;
import java.util.List;

@Entity
@Table(name = "areas")
@Data
public class Area {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre", nullable = false)
    private String name;

    @ManyToOne
    @JoinColumn(name = "empresa_id")
    private Company company;

    @OneToMany(mappedBy = "area", cascade = CascadeType.ALL)
    private List<Employee> employees;
}