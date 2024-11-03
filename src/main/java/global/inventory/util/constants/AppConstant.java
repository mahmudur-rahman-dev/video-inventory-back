package global.inventory.util.constants;

public class AppConstant {
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String[] API_ENDPOINTS_WHITELIST = {
            "/api/v1/auth/login",
            "/api/v1/auth/refresh-token"
    };
}
