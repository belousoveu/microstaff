package belousov.eu.companyservice.service;

import belousov.eu.companyservice.model.dto.CompanyDto;
import org.springframework.data.domain.Page;

public interface CompanyService {
    CompanyDto getCompanyById(int id);

    CompanyDto getCompanyByName(String name);

    Page<CompanyDto> getAllCompanies(int page, int size);
}
