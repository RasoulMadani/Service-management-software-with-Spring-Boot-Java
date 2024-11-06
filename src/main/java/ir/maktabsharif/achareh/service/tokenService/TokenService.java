package ir.maktabsharif.achareh.service.tokenService;

public interface TokenService {
    Boolean  createVerificationToken();

    boolean activateAccount(String token);
}
