package com.system.pma.repository;

import com.system.pma.model.WorkReportFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface WorkReportFileRepository extends JpaRepository<WorkReportFile, Long> {
    // Aquí puedes agregar métodos de consulta personalizados si los necesitas
    List<WorkReportFile> findByWorkReportEmployeeId(Long employeeId);

}
