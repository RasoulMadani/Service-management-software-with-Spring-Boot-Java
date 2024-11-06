package ir.maktabsharif.achareh.service.dutyService;

import ir.maktabsharif.achareh.dto.duty.DutyRequestDto;
import ir.maktabsharif.achareh.dto.duty.DutyResponseDto;
import ir.maktabsharif.achareh.dto.subDuty.SubDutyResponseDto;
import ir.maktabsharif.achareh.entity.Duty;
import ir.maktabsharif.achareh.entity.SubDuty;
import ir.maktabsharif.achareh.exception.RuleException;
import ir.maktabsharif.achareh.repository.DutyJpaRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DutyServiceImpl implements DutyService {
    private final DutyJpaRepository dutyJpaRepository;

    @Override
    @Transactional
    public DutyResponseDto save(DutyRequestDto dutyRequestDto) {
        Optional<Duty> findByName = dutyJpaRepository.findByName(dutyRequestDto.name());
        if (findByName.isPresent()) throw new RuleException("name.is.exist");


        Duty duty = new Duty(dutyRequestDto.name());

        duty = dutyJpaRepository.save(duty);
        return new DutyResponseDto(duty.getId(),duty.getName());
    }

    @Override
    public List<SubDutyResponseDto> getSubDuties(Long id) {
        Duty duty = dutyJpaRepository.findById(id)
                .orElseThrow(() -> new RuleException("duty.not.found"));

        return this.getAllSubDutiesDto(duty.getSubDuties(), id);

    }

    @Override
    public List<DutyResponseDto> getDuties() {
        List<Duty> duty = dutyJpaRepository.findAll();
        return this.getAllDutiesDto(duty);
    }

    private SubDutyResponseDto convertToSubDutyDto(SubDuty subDuty, Long duty_id) {
        return new SubDutyResponseDto(subDuty.getId(), duty_id, subDuty.getName(), subDuty.getDefinition(), subDuty.getBase_price());
    }

    public List<SubDutyResponseDto> getAllSubDutiesDto(List<SubDuty> subDuties, Long duty_id) {
        return subDuties.stream()
                .map((subDuty) -> convertToSubDutyDto(subDuty, duty_id))
                .collect(Collectors.toList());
    }

    private DutyResponseDto convertToDutyResponseDto(Duty duty) {
        return new DutyResponseDto(duty.getId(),duty.getName());
    }

    public List<DutyResponseDto> getAllDutiesDto(List<Duty> duties) {
        return duties.stream()
                .map(this::convertToDutyResponseDto)
                .collect(Collectors.toList());
    }
}
