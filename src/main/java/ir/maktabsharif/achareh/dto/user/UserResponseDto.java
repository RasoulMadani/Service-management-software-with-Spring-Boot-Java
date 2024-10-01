package ir.maktabsharif.achareh.dto.user;

import ir.maktabsharif.achareh.enums.RoleUserEnum;
import ir.maktabsharif.achareh.enums.StatusUserEnum;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record UserResponseDto(
        Long id,
        String name,
        String username,
        String email,

        RoleUserEnum role,
        StatusUserEnum status
) {
}
