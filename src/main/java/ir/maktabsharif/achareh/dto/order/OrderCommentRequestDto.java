package ir.maktabsharif.achareh.dto.order;

import jakarta.validation.constraints.*;

import java.time.LocalDate;
import java.time.LocalTime;

public record OrderCommentRequestDto(

        @Size(min = 40,max = 500, message = "{message.Must.Between.40.to.500.Characters}")
        String message,

        @NotNull(message = "{order.Id.Must.Not.Be.Null}")
        Long order_id

) {
}
