package global.inventory.service;

import global.inventory.model.user.User;
import global.inventory.payload.request.UserRegistrationRequest;

import java.util.Optional;

public interface UserService {
    Optional<User> getUserByUsername(String username);

    User createUser(UserRegistrationRequest userRegistrationRequest);

    Boolean existsByUsername(String username);
}
