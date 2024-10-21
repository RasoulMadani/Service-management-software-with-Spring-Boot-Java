package ir.maktabsharif.achareh.dto.user;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRestLoginRequest {
    private String username;
    private String password;
}
