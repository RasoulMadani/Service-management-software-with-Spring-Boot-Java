package ir.maktabsharif.achareh.controller;

import io.swagger.v3.oas.annotations.Parameter;
import ir.maktabsharif.achareh.dto.user.UserDTO;
import ir.maktabsharif.achareh.dto.user.UserRequestDto;
import ir.maktabsharif.achareh.dto.user.UserResponseDto;
import ir.maktabsharif.achareh.entity.User;
import ir.maktabsharif.achareh.enums.OrderStatusEnum;
import ir.maktabsharif.achareh.enums.RoleUserEnum;
import ir.maktabsharif.achareh.enums.StatusUserEnum;
import ir.maktabsharif.achareh.service.UserService.UserService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<User> save(@Valid @RequestBody UserRequestDto userRequestDto) {

        return ResponseEntity.ok(userService.save(userRequestDto));
    }

    @PatchMapping("/confirmed_user")
    public ResponseEntity<UserResponseDto> confirmedUser(@RequestParam Long id) {

        return ResponseEntity.ok(userService.confirmedUser(id));
    }

    @GetMapping("/users/search")
    public List<UserDTO> searchUsers(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String username,
            @Valid @RequestParam(required = false) @Email(message = "{invalid.email.format}") String email,
            @RequestParam(required = false) StatusUserEnum statusUser,
            @RequestParam(required = false) RoleUserEnum role,
            @RequestParam(required = false) String subDutyName,
            @RequestParam(required = false) String dutyName,
            @Parameter(description = "true for Asc , false for Desc", required = false)
            @RequestParam(required = false) boolean orderByScore

    ) {
        return userService.searchUsers1(
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