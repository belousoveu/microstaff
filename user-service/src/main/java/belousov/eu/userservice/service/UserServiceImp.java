package belousov.eu.userservice.service;

import belousov.eu.userservice.exception.UserNotFoundException;
import belousov.eu.userservice.model.dto.UserDto;
import belousov.eu.userservice.model.mapper.UserMapper;
import belousov.eu.userservice.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class UserServiceImp implements UserService {

    private final UserMapper userMapper;
    private final UserRepository userRepository;

    @Override
    public List<UserDto> getAllUsers() {
        return userRepository.findAll().stream().map(userMapper::toDto).toList();
    }

    @Override
    public UserDto getUserById(int id) {
        return userMapper.toDto(
                userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id))
        );
    }

    @Override
    public UserDto getUserByLastName(String lastName) {
        return userMapper.toDto(
                userRepository.findByLastName(lastName).orElseThrow(() -> new UserNotFoundException(lastName))
        );
    }
}
