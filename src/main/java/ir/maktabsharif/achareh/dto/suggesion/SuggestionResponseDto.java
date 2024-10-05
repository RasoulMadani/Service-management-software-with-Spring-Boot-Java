package ir.maktabsharif.achareh.dto.suggesion;

import java.time.LocalDate;
import java.time.LocalTime;

public record SuggestionResponseDto(
        Long id,


        Long user_id,
        Long order_id,
        Double suggestionPrice,
        LocalDate date,

        LocalTime time,
        String durationOfWork
) {
}
