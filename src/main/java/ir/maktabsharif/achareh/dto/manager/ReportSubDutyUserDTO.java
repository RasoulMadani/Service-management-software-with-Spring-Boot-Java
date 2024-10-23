package ir.maktabsharif.achareh.dto.manager;

import ir.maktabsharif.achareh.enums.OrderStatusEnum;
import ir.maktabsharif.achareh.enums.RoleUserEnum;
import ir.maktabsharif.achareh.enums.StatusUserEnum;

import java.time.LocalDate;
import java.time.LocalTime;

public record ReportSubDutyUserDTO(
         LocalDate customerStartDateSuggestion,
         LocalTime customerStartTimeSuggestion,

         LocalDate specialistStartDateSuggestion,
         LocalTime specialistStartTimeSuggestion,
         Integer durationOfWork,
         Double suggestionPriceCustomer,

         Double suggestionPriceSpecialist,
         OrderStatusEnum status,
         String subDutyName,
         String dutyName
) {

}
