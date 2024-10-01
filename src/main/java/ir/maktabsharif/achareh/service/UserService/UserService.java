package ir.maktabsharif.achareh.service.UserService;

import ir.maktabsharif.achareh.dto.user.UserRequestDto;
import ir.maktabsharif.achareh.dto.user.UserResponseDto;
import ir.maktabsharif.achareh.entity.User;

public interface UserService {
    User save(UserRequestDto userRequestDto);
    UserResponseDto confirmedUser(Long id);
}
