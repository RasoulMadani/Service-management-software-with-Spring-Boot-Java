package ir.maktabsharif.achareh.controller;

import ir.maktabsharif.achareh.dto.user.UserDTO;
import ir.maktabsharif.achareh.dto.user.UserRequestDto;
import ir.maktabsharif.achareh.dto.user.UserResponseDto;
import ir.maktabsharif.achareh.entity.User;
import ir.maktabsharif.achareh.enums.StatusUserEnum;
import ir.maktabsharif.achareh.service.UserService.UserService;
import jakarta.validation.Valid;
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
            @RequestParam(required = false) String  username,
            @RequestParam(required = false) String city) {
        return userService.searchUsers1(name, username, city);
    }

}