package com.system.pma.service;

import com.system.pma.dto.CompanyDto;
import com.system.pma.exception.ResourceNotFoundException;
import com.system.pma.model.Company;
import com.system.pma.repository.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CompanyService {

    private final CompanyRepository companyRepository;

    @Autowired
    public CompanyService(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    public List<CompanyDto> getAllCompanies() {
        return companyRepository.findAll().stream().map(this::convertToDto).collect(Collectors.toList());
    }

    public CompanyDto getCompanyById(Long id) {
        Company company = companyRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Empresa no encontrada con el ID: " + id));
        return convertToDto(company);
    }

    public CompanyDto saveCompany(CompanyDto companyDto) {
        Company company = new Company();
        company.setName(companyDto.getName());
        company.setDescription(companyDto.getDescription());
        company.setAvatar(companyDto.getAvatar());
        return convertToDto(companyRepository.save(company));
    }

    public CompanyDto updateCompany(Long id, CompanyDto companyDto) {
        Company company = companyRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Empresa no encontrada con el ID: " + id));
        company.setName(companyDto.getName());
        company.setDescription(companyDto.getDescription());
        company.setAvatar(companyDto.getAvatar());
        return convertToDto(companyRepository.save(company));
    }

    public void deleteCompany(Long id) {
        Company company = companyRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Empresa no encontrada con el ID: " + id));
        companyRepository.delete(company);
    }

    private CompanyDto convertToDto(Company company) {
        CompanyDto companyDto = new CompanyDto();
        companyDto.setId(company.getId());
        companyDto.setName(company.getName());
        companyDto.setDescription(company.getDescription());
        companyDto.setAvatar(company.getAvatar());
        return companyDto;
    }
}