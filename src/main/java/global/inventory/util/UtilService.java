package global.inventory.util;

import global.inventory.exception.CustomSecurityException;
import global.inventory.util.constants.ApiMessages;
import global.inventory.util.security.CustomUserDetails;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;

@Slf4j
public class UtilService {
    public static Long getRequesterUserIdFromSecurityContext() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication.getPrincipal() instanceof CustomUserDetails) {
            return ((CustomUserDetails) authentication.getPrincipal()).getUserId();
        }

        throw new CustomSecurityException(ApiMessages.ERROR_SECURITY_CONTEXT);
    }
}
