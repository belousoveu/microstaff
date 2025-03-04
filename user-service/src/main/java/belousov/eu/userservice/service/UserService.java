package belousov.eu.userservice.service;

import belousov.eu.userservice.model.dto.UserDto;

import java.util.List;

public interface UserService {
    List<UserDto> getAllUsers();

    UserDto getUserById(int id);

    UserDto getUserByLastName(String lastName);

    List<UserDto> getEmployeesByCompanyId(int companyId);
}
