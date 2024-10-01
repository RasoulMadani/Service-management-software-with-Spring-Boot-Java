package ir.maktabsharif.achareh.service.dutyService;

import ir.maktabsharif.achareh.dto.duty.DutyFindSubDutyRequestDto;
import ir.maktabsharif.achareh.dto.duty.DutyRequestDto;
import ir.maktabsharif.achareh.dto.duty.DutyResponseDto;
import ir.maktabsharif.achareh.dto.subDuty.SubDutyResponseDto;
import ir.maktabsharif.achareh.dto.user.UserRequestDto;
import ir.maktabsharif.achareh.entity.User;

import java.util.List;

public interface DutyService {
    DutyResponseDto save(DutyRequestDto dutyRequestDto);
    List<SubDutyResponseDto> getSubDuties(Long id);
}
