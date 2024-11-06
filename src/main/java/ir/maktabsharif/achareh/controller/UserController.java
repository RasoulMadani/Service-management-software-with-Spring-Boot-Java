package ir.maktabsharif.achareh.controller;

import io.swagger.v3.oas.annotations.Parameter;
import ir.maktabsharif.achareh.dto.user.UserDTO;
import ir.maktabsharif.achareh.dto.user.UserRequestDto;
import ir.maktabsharif.achareh.dto.user.UserResponseDto;
import ir.maktabsharif.achareh.enums.RoleUserEnum;
import ir.maktabsharif.achareh.enums.StatusUserEnum;
import ir.maktabsharif.achareh.service.UserService.UserService;
import ir.maktabsharif.achareh.utils.ApiResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    @PreAuthorize("hasAuthority('REGISTER USER')")
    public ResponseEntity<ApiResponse> save(@Valid @RequestBody UserRequestDto userRequestDto) {
        userService.save(userRequestDto);
        return ResponseEntity.ok(new ApiResponse("user.saved.successfully",true));
    }

    @PatchMapping("/confirmed_user")
    @PreAuthorize("hasAuthority('CONFIRMED USER')")
    public ResponseEntity<UserResponseDto> confirmedUser(@RequestParam Long id) {

        return ResponseEntity.ok(userService.confirmedUser(id));
    }

    @GetMapping("/users/search")
    @PreAuthorize("hasAuthority('SEARCH USER')")
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