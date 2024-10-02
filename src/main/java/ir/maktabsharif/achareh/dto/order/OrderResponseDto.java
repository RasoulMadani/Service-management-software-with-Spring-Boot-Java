package ir.maktabsharif.achareh.dto.order;

import jakarta.validation.constraints.*;

import java.time.LocalDate;
import java.time.LocalTime;

public record OrderResponseDto(
        Long id,
        Long user_id,
        Long sub_duty_id,
        Double suggestionPrice,
        String description,

        String province,
        String city,
        String street,
        String addressDetails,
        LocalDate date,
        LocalTime time

) {

}
