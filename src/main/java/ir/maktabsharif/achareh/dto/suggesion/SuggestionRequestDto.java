package ir.maktabsharif.achareh.dto.suggesion;

import jakarta.validation.constraints.*;

import java.time.LocalDate;
import java.time.LocalTime;

public record SuggestionRequestDto(
        @NotNull(message = "{suggestion.Price.Must.Not.Be.Null}")
        @Min(value = 0, message = "{suggestion.Price.Cannot.Be.Negative}")
        Double suggestionPrice,

        @NotNull(message = "{order_id.Id.Must.Not.Be.Null}")
        Long order_id,

        @NotNull(message = "{Date.Must.Not.Be.Null}")
        @FutureOrPresent(message = "{Date.Must.Be.Today.Or.In.The.Future}")
        LocalDate date,

        @NotNull(message = "{Time.Must.Not.Be.Null}")
        LocalTime time,
        @NotNull(message = "{duration_of_work.Must.Not.Be.Null}")
        @Min(value = 1,message = "{duration_of_work.Must.equal.or.greater.than.1}")
        Integer durationOfWork
) {
}
