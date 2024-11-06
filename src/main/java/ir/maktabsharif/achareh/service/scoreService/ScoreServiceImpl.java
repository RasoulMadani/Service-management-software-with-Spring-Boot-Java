package ir.maktabsharif.achareh.service.scoreService;

import ir.maktabsharif.achareh.entity.User;
import ir.maktabsharif.achareh.repository.ScoreJpaRepository;
import ir.maktabsharif.achareh.service.UserService.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ScoreServiceImpl implements ScoreService{
    private final ScoreJpaRepository scoreJpaRepository;
    @Override
    public Long getScore() {
        CustomUserDetails customUserDetails =
                (CustomUserDetails)  SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        User user = customUserDetails.getUser();
        return   scoreJpaRepository.sumAmountsByUserId(user.getId());
    }
}
