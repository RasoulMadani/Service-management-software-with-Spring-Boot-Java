package ir.maktabsharif.achareh.service.dutyService;

import ir.maktabsharif.achareh.dto.duty.DutyRequestDto;
import ir.maktabsharif.achareh.dto.duty.DutyResponseDto;
import ir.maktabsharif.achareh.dto.user.UserRequestDto;
import ir.maktabsharif.achareh.entity.User;

public interface DutyService {
    DutyResponseDto save(DutyRequestDto dutyRequestDto);
}
