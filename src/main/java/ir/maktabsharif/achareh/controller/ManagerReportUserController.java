package ir.maktabsharif.achareh.controller;

import io.swagger.v3.oas.annotations.Parameter;
import ir.maktabsharif.achareh.dto.manager.ReportSubDutyUserDTO;
import ir.maktabsharif.achareh.dto.manager.ReportUserDTO;
import ir.maktabsharif.achareh.enums.OrderStatusEnum;
import ir.maktabsharif.achareh.enums.RoleUserEnum;
import ir.maktabsharif.achareh.enums.StatusUserEnum;
import ir.maktabsharif.achareh.service.ManagerService.ReportUserService.ReportUserService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/manager/report/user")
@RequiredArgsConstructor
@PreAuthorize("hasAuthority('REPORT_USERS')")
public class ManagerReportUserController {
    private  final ReportUserService managerReportUserService;
    @GetMapping
    public List<?> reportUser(
            @RequestParam(required = false) Long user_id,
            @RequestParam(required = false) LocalDate startDateOrder,
            @RequestParam(required = false) LocalDate endDateOrder,
            @RequestParam(required = false) LocalDate  registrationUserStartDate,
            @RequestParam(required = false) LocalDate  registrationUserEndDate,
            @RequestParam(required = false) OrderStatusEnum orderStatus,
            @RequestParam(required = false) boolean countOrder,
            @RequestParam(required = false) boolean countPerformedOrder
    ){
        return managerReportUserService.reportUser(
                user_id,
                startDateOrder,
                endDateOrder,
                registrationUserStartDate,
                registrationUserEndDate,
                orderStatus,
                countOrder,
                countPerformedOrder
        );
    }
    @GetMapping("/order")
    public List<ReportSubDutyUserDTO> reportSubDutyUser(
            @RequestParam(required = false) Long user_id,
            @RequestParam(required = false) LocalDate startDate,
            @RequestParam(required = false) LocalDate endDate,
            @RequestParam(required = false) OrderStatusEnum orderStatus,
            @RequestParam(required = false) String subDutyName,
            @RequestParam(required = false) String dutyName
    ){
        return managerReportUserService.reportSubDutiesUser(
                user_id,
                startDate,
                endDate,
                orderStatus,
                subDutyName,
                dutyName

        );
    }
}
