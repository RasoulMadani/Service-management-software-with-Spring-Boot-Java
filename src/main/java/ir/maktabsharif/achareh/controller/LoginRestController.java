package ir.maktabsharif.achareh.controller;

import ir.maktabsharif.achareh.dto.user.UserRestLoginRequest;
import ir.maktabsharif.achareh.dto.user.UserRestLoginResponse;
import ir.maktabsharif.achareh.service.UserService.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rest/v1")
@RequiredArgsConstructor
public class LoginRestController {
    private final UserService userService;
    private final AuthenticationManager authenticationManager;

    @PostMapping
    public ResponseEntity<UserRestLoginResponse> login(@RequestBody UserRestLoginRequest userLoginRequest){
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userLoginRequest.getUsername(),userLoginRequest.getPassword()));
       UserRestLoginResponse userRestLoginResponse = userService.login(userLoginRequest);
       return ResponseEntity.ok(userRestLoginResponse);
    }
}
