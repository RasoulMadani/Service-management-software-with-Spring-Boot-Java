package ir.maktabsharif.achareh.service.UserService;


import ir.maktabsharif.achareh.dto.user.UserDTO;
import ir.maktabsharif.achareh.dto.user.UserRequestDto;
import ir.maktabsharif.achareh.dto.user.UserResponseDto;
import ir.maktabsharif.achareh.entity.User;
import ir.maktabsharif.achareh.enums.OrderStatusEnum;
import ir.maktabsharif.achareh.enums.RoleUserEnum;
import ir.maktabsharif.achareh.enums.StatusUserEnum;
import ir.maktabsharif.achareh.exception.RuleException;
import ir.maktabsharif.achareh.repository.userRepository.UserCriteriaRepository;
import ir.maktabsharif.achareh.repository.userRepository.UserJpaRepository;
import ir.maktabsharif.achareh.utils.UserSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserJpaRepository userRepository;
    private final UserCriteriaRepository userCriteriaRepository;



    @Override
    public User save(UserRequestDto userRequestDto) {

        Optional<User> byUsername = userRepository.findByUsername(userRequestDto.username());
        if(byUsername.isPresent())throw  new RuleException("username.is.exist");

        Optional<User> byEmail = userRepository.findByEmail(userRequestDto.email());
        if(byEmail.isPresent())throw  new RuleException("email.is.exist");

        RoleUserEnum roleUserEnum = userRequestDto.role() == RoleUserEnum.CUSTOMER ? RoleUserEnum.CUSTOMER : RoleUserEnum.SPECIALIST;
        User user = User.builder()
                .name(userRequestDto.name())
                .email(userRequestDto.email())
                .username(userRequestDto.username())
                .password(userRequestDto.password())
                .status(StatusUserEnum.NEW_USER)
                .role(roleUserEnum)
                .build();

        return userRepository.save(user);
    }

    @Override
    public UserResponseDto confirmedUser(Long id) {
        User user = userRepository
                .findById(id)
                .orElseThrow(() -> new RuleException("user.not.found"));


        user.setStatus(StatusUserEnum.CONFIRMED);

        user = userRepository.save(user);
        return new UserResponseDto(user.getId(), user.getName(), user.getUsername(), user.getEmail(), user.getRole(),user.getStatus());

    }
    public List<User> searchUsers(String name, StatusUserEnum status, String email) {
        return userRepository.findAll(UserSpecification.getSpecifications(name, status, email));
    }
    public List<UserDTO> searchUsers1(String name, String username, String email, StatusUserEnum statusUser,RoleUserEnum role,String subDutyName,String dutyName,
    boolean orderByScore

    ) {
        return userCriteriaRepository.findUsersWithCriteria(
                name,
                username,
                email,
                statusUser,
                role,
                subDutyName,
                dutyName,
                orderByScore
        );
    }
}
