package com.system.pma.repository;

import com.system.pma.model.WorkReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WorkReportRepository extends JpaRepository<WorkReport, Long> {
}
