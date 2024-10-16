package ir.maktabsharif.achareh.service.scoreService;

import ir.maktabsharif.achareh.repository.ScoreJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ScoreServiceImpl implements ScoreService{
    private final ScoreJpaRepository scoreJpaRepository;
    @Override
    public Long getScore(Long userId) {
      return   scoreJpaRepository.sumAmountsByUserId(userId);

    }
}
