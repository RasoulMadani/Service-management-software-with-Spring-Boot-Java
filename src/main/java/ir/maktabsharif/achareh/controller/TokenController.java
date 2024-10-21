package ir.maktabsharif.achareh.controller;

import ir.maktabsharif.achareh.service.tokenService.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/token")
@RequiredArgsConstructor
public class TokenController {
    private final TokenService tokenService;
    @GetMapping("/createVerificationToken")
    public String createVerificationToken(@RequestParam("user_id") Long userId) {
      return   tokenService.createVerificationToken(userId);
    }

    @GetMapping("/activate")
    public String activateAccount(@RequestParam("token") String token) {
       return tokenService.activateAccount(token);
    }
}
