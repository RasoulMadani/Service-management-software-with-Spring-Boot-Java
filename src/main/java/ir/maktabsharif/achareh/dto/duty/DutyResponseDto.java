package ir.maktabsharif.achareh.dto.duty;

import ir.maktabsharif.achareh.enums.RoleUserEnum;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record DutyResponseDto(
        String name
) {
}
