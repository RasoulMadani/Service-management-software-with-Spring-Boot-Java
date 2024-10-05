package ir.maktabsharif.achareh.dto.suggesion;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.time.LocalTime;

public record SuggestionResponseDto(
        Long di,


        Long user_id,
        Long order_id,
        Double suggestionPrice,
        LocalDate date,

        LocalTime time,
        String durationOfWork
) {
}
