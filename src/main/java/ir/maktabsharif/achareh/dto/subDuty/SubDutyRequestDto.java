package ir.maktabsharif.achareh.dto.subDuty;

import jakarta.validation.constraints.*;

public record SubDutyRequestDto(
        @NotNull(message = "{duty_id.is.empty}")
        @Min(value = 1,message = "{duty_id.must.be.positive}")
        Long duty_id,
        @NotEmpty(message = "{name.is.empty}")
        @Size(min = 3, max = 25, message = "{name.must.be.between.3.and.25.characters}")
        String name,
        @NotEmpty(message = "{definition.is.empty}")
        @Size(min = 3, max = 25, message = "{definition.must.at.least.15.characters}")
        String definition,
        @PositiveOrZero(message = "{base_price.must.be.positive.or.zero}")
        double base_price
) {
}
