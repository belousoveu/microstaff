package belousov.eu.companyservice.model.mapper;

import belousov.eu.companyservice.model.dto.CompanyDto;
import belousov.eu.companyservice.model.entity.Company;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class CompanyMapper {

    ModelMapper modelMapper;

    public CompanyDto toDto(Company company) {
        return modelMapper.map(company, CompanyDto.class);
    }
}
