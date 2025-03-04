package belousov.eu.companyservice.model.dto;

import lombok.Data;

import java.util.Set;

@Data
public class CompanyDto {

    private int id;
    private String name;
    private long budget;
    private Set<EmployeeDto> employees;
}
