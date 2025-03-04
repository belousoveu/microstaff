package belousov.eu.userservice.exception;

public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException(int id) {
        super(String.format("User with id %d not found", id));
    }

    public UserNotFoundException(String lastName) {
        super(String.format("User with last name %s not found", lastName));
    }
}
