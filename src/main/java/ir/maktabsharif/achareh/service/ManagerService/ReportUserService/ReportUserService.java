package ir.maktabsharif.achareh.service.ManagerService.ReportUserService;

import ir.maktabsharif.achareh.dto.manager.ReportSubDutyUserDTO;
import ir.maktabsharif.achareh.dto.manager.ReportUserDTO;
import ir.maktabsharif.achareh.enums.OrderStatusEnum;

import java.time.LocalDate;
import java.util.List;

public interface ReportUserService {

    List<ReportSubDutyUserDTO> reportSubDutiesUser(Long user_id,LocalDate startDate, LocalDate endDate, OrderStatusEnum orderStatus, String subDutyName, String dutyName);

    List<?> reportUser(
            Long userId,
            LocalDate startDate,
            LocalDate endDate,
            LocalDate registrationUserStartDate,
            LocalDate registrationUserEndDate,
            OrderStatusEnum orderStatus,
            boolean countOrder,
            boolean countPerformedOrder
    );
}
