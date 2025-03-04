package belousov.eu.userservice.service;

import belousov.eu.userservice.exception.UserNotFoundException;
import belousov.eu.userservice.model.dto.UserDto;
import belousov.eu.userservice.model.entity.User;
import belousov.eu.userservice.model.mapper.UserMapper;
import belousov.eu.userservice.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataRetrievalFailureException;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImpTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private UserServiceImp userService;

    private User user1, user2;
    private UserDto userDto1, userDto2;

    @BeforeEach
    void setUp() {
        user1 = new User(1, "name1", "lastname1", "1111", 1);
        user2 = new User(2, "name2", "lastname2", "2222", 2);
        userDto1 = UserDto.builder().id(1).firstName("name1").lastName("lastname1").phoneNumber("1111").build();
        userDto2 = UserDto.builder().id(2).firstName("name2").lastName("lastname2").phoneNumber("2222").build();
    }

    @Test
    void test_getAllUsers_success() {

        when(userRepository.findAll()).thenReturn(List.of(user1, user2));
        when(userMapper.toDto(user1)).thenReturn(userDto1);
        when(userMapper.toDto(user2)).thenReturn(userDto2);

        List<UserDto> actual = userService.getAllUsers();

        assertNotNull(actual);
        assertEquals(2, actual.size());
        assertEquals(userDto1, actual.get(0));
        assertEquals(userDto2, actual.get(1));
        verify(userRepository, times(1)).findAll();
        verify(userMapper, times(2)).toDto(any(User.class));

    }

    @Test
    void test_getAllUsers_SQLException() {
        doThrow(new DataRetrievalFailureException("")).when(userRepository).findAll();

        assertThrows(DataAccessException.class, () -> userService.getAllUsers());
    }

    @Test
    void test_getUserById_success() {
        when(userRepository.findById(1)).thenReturn(java.util.Optional.of(user1));
        when(userMapper.toDto(user1)).thenReturn(userDto1);

        UserDto actual = userService.getUserById(1);

        assertNotNull(actual);
        assertEquals(userDto1, actual);
        verify(userRepository, times(1)).findById(1);
    }

    @Test
    void test_getUserById_whenUserNotFound() {
        when(userRepository.findById(1)).thenReturn(java.util.Optional.empty());
        assertThrows(UserNotFoundException.class, () -> userService.getUserById(1));
    }

    @Test
    void test_getUserById_whenSQLException() {
        doThrow(new DataRetrievalFailureException("")).when(userRepository).findById(1);
        assertThrows(DataAccessException.class, () -> userService.getUserById(1));
    }

    @Test
    void test_getUserByLastName_success() {
        when(userRepository.findByLastName("lastname1")).thenReturn(java.util.Optional.of(user1));
        when(userMapper.toDto(user1)).thenReturn(userDto1);

        UserDto actual = userService.getUserByLastName("lastname1");

        assertNotNull(actual);
        assertEquals(userDto1, actual);
        verify(userRepository, times(1)).findByLastName("lastname1");
    }

    @Test
    void test_getUserByLastName_whenUserNotFound() {
        when(userRepository.findByLastName("lastname1")).thenReturn(java.util.Optional.empty());
        assertThrows(UserNotFoundException.class, () -> userService.getUserByLastName("lastname1"));
    }

    @Test
    void test_getUserByLastName_whenSQLException() {
        doThrow(new DataRetrievalFailureException("")).when(userRepository).findByLastName("lastname1");
        assertThrows(DataAccessException.class, () -> userService.getUserByLastName("lastname1"));
    }

    @Test
    void test_getEmployeesByCompanyId_success() {
        when(userRepository.findByCompanyId(1)).thenReturn(List.of(user1));
        when(userMapper.toDto(user1)).thenReturn(userDto1);

        List<UserDto> actual = userService.getEmployeesByCompanyId(1);

        assertNotNull(actual);
        assertEquals(1, actual.size());
        assertEquals(userDto1, actual.get(0));

    }

    @Test
    void test_getEmployeesByCompanyId_whenSQLException() {
        doThrow(new DataRetrievalFailureException("")).when(userRepository).findByCompanyId(1);
        assertThrows(DataAccessException.class, () -> userService.getEmployeesByCompanyId(1));
    }

}