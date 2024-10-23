package ir.maktabsharif.achareh.service.ManagerService.ReportUserService;

import ir.maktabsharif.achareh.dto.manager.ReportSubDutyUserDTO;
import ir.maktabsharif.achareh.dto.manager.ReportUserDTO;
import ir.maktabsharif.achareh.enums.OrderStatusEnum;
import ir.maktabsharif.achareh.repository.userRepository.ReportSubDutyUserCriteriaRepository;
import ir.maktabsharif.achareh.repository.userRepository.ReportUserCriteriaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReportUserServiceImpl implements ReportUserService{
    private final ReportSubDutyUserCriteriaRepository reportSubDutyUserCriteriaRepository;
    private final ReportUserCriteriaRepository reportUserCriteriaRepository;
    @Override
    public List<ReportSubDutyUserDTO> reportSubDutiesUser(
            Long user_id,
            LocalDate startDate,
            LocalDate endDate,
            OrderStatusEnum orderStatus,
            String subDutyName,
            String dutyName
    ) {
        return reportSubDutyUserCriteriaRepository.findSubDutiesWithCriteria(
                user_id,
                startDate,
                endDate,
                orderStatus,
                subDutyName,
                dutyName
        );
    }

    @Override
    public List<?> reportUser(
            Long userId,
            LocalDate startDate,
            LocalDate endDate,
            LocalDate registrationUserStartDate,
            LocalDate registrationUserEndDate,
            OrderStatusEnum orderStatus,
            boolean countOrder,
            boolean countPerformedOrder
    ) {
        return reportUserCriteriaRepository.reportUsersWithCriteria(
                userId,
                startDate,
                endDate,
                 registrationUserStartDate,
                 registrationUserEndDate,
                orderStatus,
                countOrder,
                countPerformedOrder
        );

    }
}
