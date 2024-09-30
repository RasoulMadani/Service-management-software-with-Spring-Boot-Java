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

    @Override
    public SubDutyResponseDto save(SubDutyRequestDto subDutyRequestDto) {
        Optional<SubDuty> findByName = subDutyJpaRepository.findByDutyIdAndName(1L,"allah");
        if (findByName.isPresent()) throw new RuleException("name.is.exist");

//        if (findByName.isPresent()) throw new RuleException("{name.is.exist}");
//
//
//        Duty duty = new Duty(dutyRequestDto.name());
//
//        Duty duty1 = dutyJpaRepository.save(duty);
//        return new DutyResponseDto(duty1.getName());
//        return null;
        return null;
    }
}
