package belousov.eu.companyservice.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Информация о сотруднике")
public class EmployeeDto {

    @Schema(description = "ID сотрудника")
    private int id;
    @Schema(description = "Имя сотрудника")
    private String firstName;
    @Schema(description = "Фамилия сотрудника")
    private String lastName;
    @Schema(description = "Номер телефона сотрудника")
    private String phoneNumber;

}
