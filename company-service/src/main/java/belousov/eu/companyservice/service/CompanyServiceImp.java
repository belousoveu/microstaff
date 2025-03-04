package belousov.eu.companyservice.service;

import belousov.eu.companyservice.exception.CompanyNotFoundException;
import belousov.eu.companyservice.model.dto.CompanyDto;
import belousov.eu.companyservice.model.mapper.CompanyMapper;
import belousov.eu.companyservice.repository.CompanyRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CompanyServiceImp implements CompanyService {

    private final CompanyRepository companyRepository;
    private final CompanyMapper companyMapper;


    @Override
    public CompanyDto getCompanyById(int id) {
        return companyMapper.toDto(companyRepository.findById(id)
                .orElseThrow(() -> new CompanyNotFoundException(id)));
    }

    @Override
    public CompanyDto getCompanyByName(String name) {
        return companyMapper.toDto(companyRepository.findByName(name)
                .orElseThrow(() -> new CompanyNotFoundException(name)));
    }

    @Override
    public Page<CompanyDto> getAllCompanies(int page, int size) {
        return companyRepository.findAll(PageRequest.of(page, size)).map(companyMapper::toDto);
    }
}
