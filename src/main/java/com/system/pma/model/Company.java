package com.system.pma.model;

import jakarta.persistence.*;
import lombok.Data;
import java.util.List;

@Entity
@Table(name = "empresas")
@Data
public class Company {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre", nullable = false)
    private String name;

    @OneToMany(mappedBy = "company", cascade = CascadeType.ALL)
    private List<Area> areas;
}
