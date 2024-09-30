package ir.maktabsharif.achareh.dto.duty;

import ir.maktabsharif.achareh.enums.RoleUserEnum;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record DutyRequestDto(
        @NotEmpty(message = "{name.is.empty}")
        @Size(min = 3, max = 25, message = "{name.must.be.between.3.and.25.characters}")
        String name
) {
}
