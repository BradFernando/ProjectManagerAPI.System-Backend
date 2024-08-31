package com.system.pma.service;

import com.system.pma.dto.AreaDto;
import com.system.pma.exception.ResourceNotFoundException;
import com.system.pma.model.Area;
import com.system.pma.model.Company;
import com.system.pma.repository.AreaRepository;
import com.system.pma.repository.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AreaService {

    private final AreaRepository areaRepository;
    private final CompanyRepository companyRepository;

    @Autowired
    public AreaService(AreaRepository areaRepository, CompanyRepository companyRepository) {
        this.areaRepository = areaRepository;
        this.companyRepository = companyRepository;
    }

    public List<AreaDto> getAllAreas() {
        return areaRepository.findAll().stream().map(this::convertToDto).collect(Collectors.toList());
    }

    public AreaDto getAreaById(Long id) {
        Area area = areaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Área no encontrada con el ID: " + id));
        return convertToDto(area);
    }

    public AreaDto saveArea(AreaDto areaDto) {
        Area area = new Area();
        area.setName(areaDto.getName());
        Company company = companyRepository.findById(areaDto.getCompanyId())
                .orElseThrow(() -> new ResourceNotFoundException("Empresa no encontrada con el ID: " + areaDto.getCompanyId()));
        area.setCompany(company);
        return convertToDto(areaRepository.save(area));
    }

    public AreaDto updateArea(Long id, AreaDto areaDto) {
        Area area = areaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Área no encontrada con el ID: " + id));
        area.setName(areaDto.getName());
        Company company = companyRepository.findById(areaDto.getCompanyId())
                .orElseThrow(() -> new ResourceNotFoundException("Empresa no encontrada con el ID: " + areaDto.getCompanyId()));
        area.setCompany(company);
        return convertToDto(areaRepository.save(area));
    }

    public void deleteArea(Long id) {
        Area area = areaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Área no encontrada con el ID: " + id));
        areaRepository.delete(area);
    }

    private AreaDto convertToDto(Area area) {
        AreaDto areaDto = new AreaDto();
        areaDto.setId(area.getId());
        areaDto.setName(area.getName());
        areaDto.setCompanyId(area.getCompany().getId());
        return areaDto;
    }
}
