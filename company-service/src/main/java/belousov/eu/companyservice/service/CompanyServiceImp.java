package belousov.eu.companyservice.service;

import belousov.eu.companyservice.api_client.UserFeignClient;
import belousov.eu.companyservice.exception.CompanyNotFoundException;
import belousov.eu.companyservice.model.dto.CompanyDto;
import belousov.eu.companyservice.model.dto.EmployeeDto;
import belousov.eu.companyservice.model.entity.Company;
import belousov.eu.companyservice.model.mapper.CompanyMapper;
import belousov.eu.companyservice.repository.CompanyRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CompanyServiceImp implements CompanyService {

    private final CompanyRepository companyRepository;
    private final CompanyMapper companyMapper;
    private final UserFeignClient userFeignClient;


    @Override
    public CompanyDto getCompanyById(int id) {
        Company company = companyRepository.findById(id).orElseThrow(() -> new CompanyNotFoundException(id));
        return requestEmployeesByCompanyId(company);
    }

    @Override
    public CompanyDto getCompanyByName(String name) {
        Company company = companyRepository.findByName(name).orElseThrow(() -> new CompanyNotFoundException(name));
        return requestEmployeesByCompanyId(company);
    }

    @Override
    public Page<CompanyDto> getAllCompanies(int page, int size) {
        return companyRepository.findAll(PageRequest.of(page, size)).map(this::requestEmployeesByCompanyId);
    }

    private CompanyDto requestEmployeesByCompanyId(Company company) {
        List<EmployeeDto> employeeDtoList = userFeignClient.getEmployeesByCompanyId(company.getId());
        CompanyDto companyDto = companyMapper.toDto(company);
        companyDto.setEmployees(employeeDtoList);
        return companyDto;

    }
}
