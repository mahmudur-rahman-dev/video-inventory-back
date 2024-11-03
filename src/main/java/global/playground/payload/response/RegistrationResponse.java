package global.playground.payload.response;

import global.playground.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class RegistrationResponse {
    private  Long userId;
    private String username;
}
