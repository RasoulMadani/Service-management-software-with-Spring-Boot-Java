package ir.maktabsharif.achareh.service.tokenService;

import ir.maktabsharif.achareh.entity.Token;
import ir.maktabsharif.achareh.entity.User;
import ir.maktabsharif.achareh.enums.StatusUserEnum;
import ir.maktabsharif.achareh.exception.RuleException;
import ir.maktabsharif.achareh.repository.TokenJpaRepository;
import ir.maktabsharif.achareh.repository.userRepository.UserJpaRepository;
import ir.maktabsharif.achareh.utils.EmailService;
import jakarta.mail.MessagingException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;
@Service
@RequiredArgsConstructor
public class TokenServiceImpl implements TokenService{
    private  final TokenJpaRepository tokenJpaRepository;
    private final UserJpaRepository userJpaRepository;
    private final EmailService emailService;
    @Override
    public String createVerificationToken(Long userId) {
        User user = userJpaRepository
                .findById(userId)
                .orElseThrow(() -> new RuleException("user.not.found"));

        if (user.getStatus() == StatusUserEnum.AWAITING || user.getStatus() == StatusUserEnum.CONFIRMED){
            throw new RuleException("user.activated.before");
        }

        String token = UUID.randomUUID().toString();
        LocalDateTime expiryDate = LocalDateTime.now().plusHours(24); // تاریخ انقضا برای 24 ساعت آینده

        Token verificationToken = new Token(token, user, expiryDate);
        tokenJpaRepository.save(verificationToken);

        String activationLink = "http://localhost:8080/token/activate?token=" + token;
        try {
            emailService.sendActivationEmail(user.getEmail(), activationLink);
        } catch (MessagingException e) {
            throw  new RuleException("cannot.send.email");
        }
        return "activation.link.send.successfully";
    }

    @Override
    @Transactional
    public String activateAccount(String token) {
        Token verificationToken = tokenJpaRepository.findByToken(token);

        if (verificationToken == null) {
            throw  new RuleException("token.is.null");
        }

        if (verificationToken.getExpiryDate().isBefore(LocalDateTime.now())) {
            throw new RuleException("activation.link.has.expired");
        }

        // فعال‌سازی کاربر
        User user = verificationToken.getUser();
        user.setStatus(StatusUserEnum.AWAITING);
        userJpaRepository.save(user);

        // حذف توکن بعد از فعال‌سازی
        tokenJpaRepository.delete(verificationToken);

        return "account.activated.successfully";
    }
}
