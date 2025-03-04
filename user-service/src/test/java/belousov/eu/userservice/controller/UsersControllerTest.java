package belousov.eu.userservice.controller;

import belousov.eu.userservice.exception.UserNotFoundException;
import belousov.eu.userservice.model.dto.UserDto;
import belousov.eu.userservice.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UsersController.class)
class UsersControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockitoBean
    UserService userService;

    @Test
    void test_getAllUsers_success() throws Exception {

        when(userService.getAllUsers()).thenReturn(
                List.of(
                        UserDto.builder().id(1).firstName("name1").lastName("lastname1").phoneNumber("1111").build(),
                        UserDto.builder().id(2).firstName("name2").lastName("lastname2").phoneNumber("2222").build()
                )
        );

        mockMvc.perform(get("/api/users/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[1].id").value(2));

    }

    @Test
    void test_getAllUsers_whenSQLException() throws Exception {

        doThrow(new DataRetrievalFailureException("SQL exception")).when(userService).getAllUsers();

        mockMvc.perform(get("/api/users/all"))
                .andExpect(status().is5xxServerError());
    }


    @Test
    void test_getUserById_success() throws Exception {

        when(userService.getUserById(1)).thenReturn(
                UserDto.builder().id(1).firstName("name1").lastName("lastname1").phoneNumber("1111").build()
        );

        mockMvc.perform(get("/api/users/id/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void test_getUserById_whenUserNotFound() throws Exception {

        doThrow(new UserNotFoundException(1)).when(userService).getUserById(1);

        mockMvc.perform(get("/api/users/id/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    void test_getUserById_whenSQLException() throws Exception {

        doThrow(new DataRetrievalFailureException("SQL exception")).when(userService).getUserById(1);

        mockMvc.perform(get("/api/users/id/1"))
                .andExpect(status().is5xxServerError());
    }


    @Test
    void test_getUserByLastName_success() throws Exception {

        when(userService.getUserByLastName("lastname1")).thenReturn(
                UserDto.builder().id(1).firstName("name1").lastName("lastname1").phoneNumber("1111").build()
        );

        mockMvc.perform(get("/api/users/name/lastname1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void test_getUserByLastName_whenUserNotFound() throws Exception {
        doThrow(new UserNotFoundException("lastname1")).when(userService).getUserByLastName("lastname1");

        mockMvc.perform(get("/api/users/name/lastname1"))
                .andExpect(status().isNotFound());
    }

    @Test
    void test_getUserByLastName_whenSQLException() throws Exception {
        doThrow(new DataRetrievalFailureException("SQL exception")).when(userService).getUserByLastName("lastname1");

        mockMvc.perform(get("/api/users/name/lastname1"))
                .andExpect(status().is5xxServerError());
    }

    @Test
    void test_getEmployeesByCompanyId_success() throws Exception {
        when(userService.getEmployeesByCompanyId(1)).thenReturn(
                List.of(
                        UserDto.builder().id(1).firstName("name1").lastName("lastname1").phoneNumber("1111").build(),
                        UserDto.builder().id(2).firstName("name2").lastName("lastname2").phoneNumber("2222").build()
                )
        );

        mockMvc.perform(get("/api/users/employee/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[1].id").value(2));

    }

    @Test
    void test_getEmployeesByCompanyId_whenSQLException() throws Exception {
        doThrow(new DataRetrievalFailureException("SQL exception")).when(userService).getEmployeesByCompanyId(1);

        mockMvc.perform(get("/api/users/employee/1"))
                .andExpect(status().is5xxServerError());
    }
}