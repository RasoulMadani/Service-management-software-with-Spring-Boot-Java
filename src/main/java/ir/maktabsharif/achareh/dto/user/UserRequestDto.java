package ir.maktabsharif.achareh.dto.user;

import ir.maktabsharif.achareh.enums.RoleUserEnum;
import jakarta.validation.constraints.*;

public record UserRequestDto(
        @NotEmpty(message = "{name.is.empty}")
        @Size(min = 3, max = 15, message = "{name.must.be.between.3.and.15.characters}")
        String name,
        @NotEmpty(message = "{username.is.empty}")
        @Size(min = 3, max = 15, message = "{username.must.be.between.3.and.15.characters}")
        String username,
        @NotEmpty(message = "{password.is.empty}")
        @Size(min = 8, message = "{password.must.be.at.least.8.characters.long}")
        @Pattern(
                regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{8,}$",
                message = "Password must contain at least one digit, one lowercase letter, one uppercase letter, and one special character"
        )
        String password,
        @NotEmpty(message = "{email.is.empty}")
        @Email(message = "{email.is.incorrect}")
        String email,

        RoleUserEnum role
) {
}
