package com.system.pma.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "archivos_reporte_trabajo")
@Data
public class WorkReportFile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Lob
    @Column(name = "archivo_pdf", nullable = false)
    private byte[] pdfFile; // Almacena el contenido del PDF

    @OneToOne
    @JoinColumn(name = "reporte_trabajo_id", nullable = false)
    private WorkReport workReport; // Relación con la entidad WorkReport

    @Column(name = "nombre_archivo", nullable = false)
    private String fileName; // Nombre del archivo

    @Column(name = "tipo_mime", nullable = false)
    private String mimeType; // Tipo MIME del archivo
}
