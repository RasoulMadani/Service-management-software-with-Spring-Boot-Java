package ir.maktabsharif.achareh.dto.duty;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record DutyFindSubDutyRequestDto(
        @NotNull(message = "{id.is.empty}")
        @Min(value = 1,message = "{id.must.be.positive}")
        Long id
) {
}
