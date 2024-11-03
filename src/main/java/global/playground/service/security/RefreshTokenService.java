package global.playground.service.security;

import global.playground.model.RefreshToken;
import global.playground.payload.request.RefreshTokenRequest;
import global.playground.payload.response.RefreshTokenResponse;
import org.springframework.http.ResponseCookie;

public interface RefreshTokenService {
    RefreshToken createRefreshToken(Long userId);

    RefreshToken verifyExpiration(RefreshToken token);

    ResponseCookie generateRefreshTokenCookie(String token);

    void deleteByToken(String token);

    ResponseCookie getCleanRefreshTokenCookie();

    RefreshTokenResponse generateNewToken(RefreshTokenRequest request);
}