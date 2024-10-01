package ir.maktabsharif.achareh.service.dutyService;

import ir.maktabsharif.achareh.dto.duty.DutyFindSubDutyRequestDto;
import ir.maktabsharif.achareh.dto.duty.DutyRequestDto;
import ir.maktabsharif.achareh.dto.duty.DutyResponseDto;
import ir.maktabsharif.achareh.dto.subDuty.SubDutyResponseDto;
import ir.maktabsharif.achareh.dto.user.UserRequestDto;
import ir.maktabsharif.achareh.entity.Duty;
import ir.maktabsharif.achareh.entity.SubDuty;
import ir.maktabsharif.achareh.entity.User;
import ir.maktabsharif.achareh.enums.RoleUserEnum;
import ir.maktabsharif.achareh.enums.StatusUserEnum;
import ir.maktabsharif.achareh.exception.RuleException;
import ir.maktabsharif.achareh.repository.DutyJpaRepository;
import ir.maktabsharif.achareh.repository.UserJpaRepository;
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
    public DutyResponseDto save(DutyRequestDto dutyRequestDto) {
        Optional<Duty> findByName = dutyJpaRepository.findByName(dutyRequestDto.name());
        if (findByName.isPresent()) throw new RuleException("{name.is.exist}");


        Duty duty = new Duty(dutyRequestDto.name());

        Duty duty1 = dutyJpaRepository.save(duty);
        return new DutyResponseDto(duty1.getName());
    }

    public List<SubDutyResponseDto> getSubDuties(Long id) {
        Duty duty = dutyJpaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("{duty.not.found}"));
        return this.getAllSubDuties(duty.getSubDuties(), id);

    }

    private SubDutyResponseDto convertToDto(SubDuty subDuty, Long duty_id) {
        return new SubDutyResponseDto(subDuty.getId(), duty_id, subDuty.getName(), subDuty.getDefinition(), subDuty.getBase_price());
    }

    public List<SubDutyResponseDto> getAllSubDuties(List<SubDuty> subDuties, Long duty_id) {
        return subDuties.stream()
                .map((subDuty) -> convertToDto(subDuty, duty_id))
                .collect(Collectors.toList());
    }
}
