package global.playground.service;

import global.playground.model.user.User;
import global.playground.payload.request.UserRegistrationRequest;

import java.util.Optional;

public interface UserService {
    Optional<User> getUserByUsername(String username);

    User createUser(UserRegistrationRequest userRegistrationRequest);

    Boolean existsByUsername(String username);
}
