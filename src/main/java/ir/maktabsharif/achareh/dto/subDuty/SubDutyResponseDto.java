package ir.maktabsharif.achareh.dto.subDuty;

import jakarta.validation.constraints.*;

public record SubDutyResponseDto(
        Long id,
        Long duty_id,
        String name,
        String definition,
        double base_price
) {
}
