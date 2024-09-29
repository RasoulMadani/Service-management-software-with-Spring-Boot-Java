package ir.maktabsharif.achareh.service.UserService;


import ir.maktabsharif.achareh.dto.user.UserRequestDto;
import ir.maktabsharif.achareh.entity.User;
import ir.maktabsharif.achareh.enums.RoleUserEnum;
import ir.maktabsharif.achareh.enums.StatusUserEnum;
import ir.maktabsharif.achareh.exception.RuleException;
import ir.maktabsharif.achareh.repository.UserJpaRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
public class UserServiceImpl implements UserService {
    private final UserJpaRepository userRepository;

    public UserServiceImpl(UserJpaRepository userRepository) {
        this.userRepository = userRepository;
    }


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
}
