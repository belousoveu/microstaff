package belousov.eu.companyservice.api_client;

import belousov.eu.companyservice.config.FeignConfig;
import belousov.eu.companyservice.model.dto.EmployeeDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "user-service", configuration = FeignConfig.class)
public interface UserFeignClient {

    @GetMapping("/api/users/employee/{companyId}")
    List<EmployeeDto> getEmployeesByCompanyId(@PathVariable("companyId") int companyId);
}
