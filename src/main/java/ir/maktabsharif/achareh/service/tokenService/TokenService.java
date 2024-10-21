package ir.maktabsharif.achareh.service.tokenService;

public interface TokenService {
    String  createVerificationToken(Long userId);

    String activateAccount(String token);
}
