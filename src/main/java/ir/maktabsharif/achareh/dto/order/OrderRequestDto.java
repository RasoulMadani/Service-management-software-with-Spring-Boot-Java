package ir.maktabsharif.achareh.dto.order;

import jakarta.validation.constraints.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;

public record OrderRequestDto(
        @NotNull(message = "{suggestion.Price.Must.Not.Be.Null}")
        @Min(value = 0, message = "{suggestion.Price.Cannot.Be.Negative}")
        Double suggestionPrice,

        @Size(min = 40,max = 500, message = "{Description.Must.Between.40.to.500.Characters}")
        String description,

        @NotNull(message = "{sub_duty.Id.Must.Not.Be.Null}")
        Long sub_duty_id,


        @NotBlank(message = "{Province.Must.Not.Be.Blank}")
        String province,

        @NotBlank(message = "{City.Must.Not.Be.Blank}")
        String city,

        @NotBlank(message = "{Street.Must.Not.Be.Blank}")
        String street,

        @Size(max = 500, message = "{Address.Details.Must.Not.Exceed.500.Characters}")
        String addressDetails,

        @NotNull(message = "{Date.Must.Not.Be.Null}")
        @FutureOrPresent(message = "{Date.Must.Be.Today.Or.In.The.Future}")
        LocalDate date,

        @NotNull(message = "{Time.Must.Not.Be.Null}")
        LocalTime time

) {
}
