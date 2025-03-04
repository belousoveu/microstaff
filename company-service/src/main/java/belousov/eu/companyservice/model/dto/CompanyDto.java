package belousov.eu.companyservice.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Информация о компании")
public class CompanyDto {

    @Schema(description = "ID компании")
    private int id;
    @Schema(description = "Название компании")
    private String name;
    @Schema(description = "Бюджет компании")
    private long budget;
    @Schema(description = "Сотрудники компании")
    private List<EmployeeDto> employees;
}
