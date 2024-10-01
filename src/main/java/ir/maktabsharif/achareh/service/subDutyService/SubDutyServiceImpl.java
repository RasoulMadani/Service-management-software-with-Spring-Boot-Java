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
        SubDuty findByDutyIdAndName =
                subDutyJpaRepository.findByDutyIdAndName(subDutyRequestDto.duty_id(), subDutyRequestDto.name())
                        .orElseThrow(()->new RuleException("{sub.duty.is.exist}"));


        Duty findDuty = dutyJpaRepository.findById(subDutyRequestDto.duty_id())
                .orElseThrow(()->new RuleException("{duty.is.not.exist}"));


        SubDuty subDuty = new SubDuty(subDutyRequestDto.name(), subDutyRequestDto.base_price(), subDutyRequestDto.definition(), findDuty);
        SubDuty subDuty1 = subDutyJpaRepository.save(subDuty);
        return new SubDutyResponseDto(subDuty1.getId(), findDuty.getId(), subDuty1.getName(), subDuty1.getDefinition(), subDuty1.getBase_price());
    }

    @Override
    public SubDutyResponseDto update(SubDutyRequestDto subDutyRequestDto) {
        SubDuty subDuty = subDutyJpaRepository
                        .findById(subDutyRequestDto.id())
                        .orElseThrow(() -> new RuleException("{sub.duty.is.not.exist}"));

        Duty duty = dutyJpaRepository
                        .findById(subDutyRequestDto.duty_id())
                        .orElseThrow(()->new RuleException("{duty.is.not.exist}"));


        subDuty.setName(subDutyRequestDto.name());
        subDuty.setBase_price(subDutyRequestDto.base_price());
        subDuty.setDefinition(subDutyRequestDto.definition());
        subDuty.setDuty(duty);

        SubDuty subDuty1 = subDutyJpaRepository.save(subDuty);
        return new SubDutyResponseDto(subDuty1.getId(), duty.getId(), subDuty1.getName(), subDuty1.getDefinition(), subDuty1.getBase_price());

    }
}
