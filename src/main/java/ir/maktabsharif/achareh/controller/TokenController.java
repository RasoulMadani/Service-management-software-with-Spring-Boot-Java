package ir.maktabsharif.achareh.controller;

import ir.maktabsharif.achareh.exception.RuleException;
import ir.maktabsharif.achareh.service.tokenService.TokenService;
import ir.maktabsharif.achareh.utils.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
    @PreAuthorize("hasAuthority('CREATE_VERIFICATION_TOKEN')")
    public ResponseEntity<ApiResponse> createVerificationToken() {
      if(tokenService.createVerificationToken()){
          return ResponseEntity.ok(new ApiResponse("verify.token.send.successfully",true));
      }else {
          throw new RuleException("something.went.wrong");
      }
    }

    @GetMapping("/activate")
    public ResponseEntity<ApiResponse> activateAccount(@RequestParam("token") String token) {
        if(tokenService.activateAccount(token)){
            return ResponseEntity.ok(new ApiResponse("token.verified.successfully",true));
        }else {
            throw new RuleException("something.went.wrong");
        }

    }
}
