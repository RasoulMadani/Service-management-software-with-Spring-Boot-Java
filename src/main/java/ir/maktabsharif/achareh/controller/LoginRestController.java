package ir.maktabsharif.achareh.controller;

import ir.maktabsharif.achareh.config.jwt.AuthenticationRequest;
import ir.maktabsharif.achareh.config.jwt.JwtUtil;
import ir.maktabsharif.achareh.dto.user.UserRestLoginRequest;
import ir.maktabsharif.achareh.dto.user.UserRestLoginResponse;
import ir.maktabsharif.achareh.exception.RuleException;
import ir.maktabsharif.achareh.service.UserService.CustomUserDetailsService;
import ir.maktabsharif.achareh.service.UserService.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/public/rest/v1")
@RequiredArgsConstructor
public class LoginRestController {
    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final CustomUserDetailsService customUserDetailsService;

//    @PostMapping
//    public ResponseEntity<UserRestLoginResponse> login(@RequestBody UserRestLoginRequest userLoginRequest){
//
//        try {
//            authenticationManager.authenticate(
//                    new UsernamePasswordAuthenticationToken(userLoginRequest.getUsername(), userLoginRequest.getPassword())
//            );
//        } catch (Exception e) {
//            throw new RuleException("invalid.credentials");
//        }
//       UserRestLoginResponse userRestLoginResponse = userService.login(userLoginRequest);
//       return ResponseEntity.ok(userRestLoginResponse);
//    }
    @PostMapping("/login")
    public String createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest) throws Exception {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword())
            );
        } catch (Exception e) {
            throw new Exception("Invalid credentials", e);
        }

        final UserDetails userDetails = customUserDetailsService.loadUserByUsername(authenticationRequest.getUsername());
        final String jwt = jwtUtil.generateToken(userDetails);

        return jwt;
    }
}
