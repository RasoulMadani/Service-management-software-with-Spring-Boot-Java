package ir.maktabsharif.achareh.dto.order;

import java.time.LocalDate;
import java.time.LocalTime;

public record OrderDetailsDto(
    Long id,
    String description,
    Double price,
    Long user_suggestion_id,
    Long user_ordered_id
    ){
}
