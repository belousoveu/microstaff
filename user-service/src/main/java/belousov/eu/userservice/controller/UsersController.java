package belousov.eu.userservice.controller;


import belousov.eu.userservice.model.dto.UserDto;
import belousov.eu.userservice.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@AllArgsConstructor
public class UsersController {

    private final UserService userService;

    @GetMapping("/all")
    public List<UserDto> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/id/{id}")
    public UserDto getUserById(@PathVariable int id) {

        return userService.getUserById(id);
    }

    @GetMapping("/name/{last_name}")
    public UserDto getUserByLastName(@PathVariable(name = "last_name") String lastName) {
        return userService.getUserByLastName(lastName);
    }

}
