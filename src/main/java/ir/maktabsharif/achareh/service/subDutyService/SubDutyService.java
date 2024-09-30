package ir.maktabsharif.achareh.service.subDutyService;


import ir.maktabsharif.achareh.dto.subDuty.SubDutyRequestDto;
import ir.maktabsharif.achareh.dto.subDuty.SubDutyResponseDto;

public interface SubDutyService {
    SubDutyResponseDto save(SubDutyRequestDto subDutyRequestDto);
}
