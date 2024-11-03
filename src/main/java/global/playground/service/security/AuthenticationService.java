package global.playground.service.security;

import global.playground.payload.request.AuthenticationRequest;
import global.playground.payload.request.UserRegistrationRequest;
import global.playground.payload.response.AuthenticationResponse;
import global.playground.payload.response.RegistrationResponse;

public interface AuthenticationService {
    AuthenticationResponse authenticate(AuthenticationRequest request);

    RegistrationResponse userRegistration(UserRegistrationRequest registerRequest);
}
