package ir.maktabsharif.achareh.service.UserService;

import ir.maktabsharif.achareh.dto.user.UserDTO;
import ir.maktabsharif.achareh.dto.user.UserRequestDto;
import ir.maktabsharif.achareh.dto.user.UserResponseDto;
import ir.maktabsharif.achareh.entity.User;
import ir.maktabsharif.achareh.enums.OrderStatusEnum;
import ir.maktabsharif.achareh.enums.RoleUserEnum;
import ir.maktabsharif.achareh.enums.StatusUserEnum;
import jakarta.validation.constraints.Email;

import java.util.List;

public interface UserService {
    User save(UserRequestDto userRequestDto);
    UserResponseDto confirmedUser(Long id);
    List<User> searchUsers(String name, StatusUserEnum status, String email);
    List<UserDTO> searchUsers1(String name, String username, String email, StatusUserEnum statusEnum ,RoleUserEnum role, String subDutyName, String dutyName
    , boolean orderByScore);
}
