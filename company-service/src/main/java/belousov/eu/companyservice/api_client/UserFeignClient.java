package belousov.eu.companyservice.api_client;

import belousov.eu.companyservice.config.FeignConfig;
import belousov.eu.companyservice.model.dto.EmployeeDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient(name = "user-service", url = "${feign.client.config.user-service.url}", configuration = FeignConfig.class)
public interface UserFeignClient {

    @GetMapping("/user/employee")
    List<EmployeeDto> getEmployeesByCompanyId(int companyId);
}
