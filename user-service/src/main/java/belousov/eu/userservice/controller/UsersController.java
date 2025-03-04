package belousov.eu.userservice.controller;


import belousov.eu.userservice.model.dto.UserDto;
import belousov.eu.userservice.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@AllArgsConstructor
@Tag(name = "Пользователи", description = "Операции с пользователями")
public class UsersController {

    private final UserService userService;

    @Operation(summary = "Получить всех пользователей")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Список пользователей успешно получен"),
            @ApiResponse(responseCode = "503", description = "Сервис временно недоступен")})
    @GetMapping("/all")
    public List<UserDto> getAllUsers() {
        return userService.getAllUsers();
    }

    @Operation(summary = "Получить пользователя по ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Пользователь успешно получен"),
            @ApiResponse(responseCode = "404", description = "Пользователь не найден"),
            @ApiResponse(responseCode = "503", description = "Сервис временно недоступен")})
    @GetMapping("/id/{id}")
    public UserDto getUserById(@PathVariable int id) {

        return userService.getUserById(id);
    }

    @Operation(summary = "Получить пользователя по Фамилии")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Пользователь успешно получен"),
            @ApiResponse(responseCode = "404", description = "Пользователь не найден"),
            @ApiResponse(responseCode = "503", description = "Сервис временно недоступен")})
    @GetMapping("/name/{last_name}")
    public UserDto getUserByLastName(@PathVariable(name = "last_name") String lastName) {
        return userService.getUserByLastName(lastName);
    }

    @Operation(summary = "Получить всех сотрудников компании по ID компании")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Сотрудники успешно получены"),
            @ApiResponse(responseCode = "503", description = "Сервис временно недоступен")})
    @GetMapping("/employee/{company_id}")
    public List<UserDto> getEmployeesByCompanyId(@PathVariable(name = "company_id") int companyId) {
        return userService.getEmployeesByCompanyId(companyId);
    }

}
