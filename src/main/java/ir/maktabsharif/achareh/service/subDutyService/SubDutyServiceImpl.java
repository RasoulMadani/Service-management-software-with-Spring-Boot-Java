package ir.maktabsharif.achareh.service.subDutyService;

import ir.maktabsharif.achareh.dto.duty.DutyRequestDto;
import ir.maktabsharif.achareh.dto.duty.DutyResponseDto;
import ir.maktabsharif.achareh.dto.subDuty.SubDutyRequestDto;
import ir.maktabsharif.achareh.dto.subDuty.SubDutyResponseDto;
import ir.maktabsharif.achareh.entity.Duty;
import ir.maktabsharif.achareh.entity.SubDuty;
import ir.maktabsharif.achareh.exception.RuleException;
import ir.maktabsharif.achareh.repository.DutyJpaRepository;
import ir.maktabsharif.achareh.repository.SubDutyJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SubDutyServiceImpl implements SubDutyService {
    private final SubDutyJpaRepository subDutyJpaRepository;
    private final DutyJpaRepository dutyJpaRepository;

    @Override
    public SubDutyResponseDto save(SubDutyRequestDto subDutyRequestDto) {
        Optional<SubDuty> findByDutyIdAndName = subDutyJpaRepository.findByDutyIdAndName(subDutyRequestDto.duty_id(), subDutyRequestDto.name());
        if (findByDutyIdAndName.isPresent()) throw new RuleException("{sub.duty.is.exist}");

        Optional<Duty> findDuty = dutyJpaRepository.findById(subDutyRequestDto.duty_id());
        if (findDuty.isEmpty()) throw new RuleException("{duty.is.not.exist}");

        SubDuty subDuty = new SubDuty(subDutyRequestDto.name(), subDutyRequestDto.base_price(), subDutyRequestDto.definition(), findDuty.get());
        SubDuty subDuty1 = subDutyJpaRepository.save(subDuty);
        return new SubDutyResponseDto(subDuty1.getId(), findDuty.get().getId(), subDuty1.getName(), subDuty1.getDefinition(), subDuty1.getBase_price());
    }
}
