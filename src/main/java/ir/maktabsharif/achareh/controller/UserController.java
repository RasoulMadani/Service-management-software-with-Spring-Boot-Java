package ir.maktabsharif.achareh.controller;

import ir.maktabsharif.achareh.dto.user.UserRequestDto;
import ir.maktabsharif.achareh.dto.user.UserResponseDto;
import ir.maktabsharif.achareh.entity.User;
import ir.maktabsharif.achareh.service.UserService.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
}