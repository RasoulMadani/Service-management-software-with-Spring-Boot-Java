package ir.maktabsharif.achareh.service.dutyService;

import ir.maktabsharif.achareh.dto.duty.DutyRequestDto;
import ir.maktabsharif.achareh.dto.duty.DutyResponseDto;
import ir.maktabsharif.achareh.dto.user.UserRequestDto;
import ir.maktabsharif.achareh.entity.Duty;
import ir.maktabsharif.achareh.entity.User;
import ir.maktabsharif.achareh.enums.RoleUserEnum;
import ir.maktabsharif.achareh.enums.StatusUserEnum;
import ir.maktabsharif.achareh.exception.RuleException;
import ir.maktabsharif.achareh.repository.DutyJpaRepository;
import ir.maktabsharif.achareh.repository.UserJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DutyServiceImpl implements DutyService {
    private final DutyJpaRepository dutyJpaRepository;

    @Override
    public DutyResponseDto save(DutyRequestDto dutyRequestDto) {
        Optional<Duty> findByName = dutyJpaRepository.findByName(dutyRequestDto.name());
        if (findByName.isPresent()) throw new RuleException("name.is.exist");


        Duty duty = new Duty(dutyRequestDto.name());

        Duty duty1 = dutyJpaRepository.save(duty);
        return new DutyResponseDto(duty1.getName());
    }
}
