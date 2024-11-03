package global.playground.controller;

import global.playground.exception.TokenNotFoundException;
import global.playground.payload.request.AuthenticationRequest;
import global.playground.payload.request.RefreshTokenRequest;
import global.playground.payload.request.UserRegistrationRequest;
import global.playground.payload.response.AuthenticationResponse;
import global.playground.payload.response.RefreshTokenResponse;
import global.playground.payload.response.RegistrationResponse;
import global.playground.payload.response.generic.PlaygroundResponse;
import global.playground.service.security.AuthenticationService;
import global.playground.service.security.JwtService;
import global.playground.service.security.RefreshTokenService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/v1/auth")
public class AuthenticationController {
    private final AuthenticationService authenticationService;
    private final JwtService jwtService;
    private final RefreshTokenService refreshTokenService;

    @PostMapping("/login")
    public ResponseEntity<PlaygroundResponse<AuthenticationResponse>> login(@RequestBody AuthenticationRequest authenticationRequest) {
        var response = authenticationService.authenticate(authenticationRequest);
        var generatedCookies = constructCookies(response);

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, generatedCookies.getLeft())
                .header(HttpHeaders.SET_COOKIE, generatedCookies.getRight())
                .body(new PlaygroundResponse<>(response));
    }

    @PostMapping("/registration")
    public ResponseEntity<PlaygroundResponse<RegistrationResponse>> userRegistration(@RequestBody @Validated UserRegistrationRequest userRegistrationRequest) {
        var register = authenticationService.userRegistration(userRegistrationRequest);
        log.info("user registration response: {}", register);
        return ResponseEntity.ok(new PlaygroundResponse<>(register));
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<PlaygroundResponse<RefreshTokenResponse>> refreshToken(@RequestBody RefreshTokenRequest request) {
        var response = refreshTokenService.generateNewToken(request);
        return ResponseEntity.ok(new PlaygroundResponse<>(response));
    }

    @PostMapping("/logout")
    public ResponseEntity<PlaygroundResponse<String>> logout(
            @RequestHeader(HttpHeaders.AUTHORIZATION) String refreshToken
    ) {
        if (refreshToken != null) {
            refreshTokenService.deleteByToken(refreshToken);

            var cookies = clearCookies();
            log.info("user logged out.............");
            return ResponseEntity.ok()
                    .header(HttpHeaders.SET_COOKIE, cookies.getLeft())
                    .header(HttpHeaders.SET_COOKIE, cookies.getRight())
                    .body(new PlaygroundResponse<>("Logged out successfully"));
        }
        throw new TokenNotFoundException("Logged out  unsuccessful");
    }

    private Pair<String, String> constructCookies(AuthenticationResponse response) {
        var jwtCookie = jwtService.generateJwtCookie(response.getAccessToken()).toString();
        var refreshTokenCookie = refreshTokenService.generateRefreshTokenCookie(response.getRefreshToken()).toString();
        return Pair.of(jwtCookie, refreshTokenCookie);
    }

    private Pair<String, String> clearCookies() {
        var jwtCookie = jwtService.getCleanJwtCookie().toString();
        var refreshTokenCookie = refreshTokenService.getCleanRefreshTokenCookie().toString();
        return Pair.of(jwtCookie, refreshTokenCookie);
    }
}
