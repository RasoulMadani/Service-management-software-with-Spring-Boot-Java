package ir.maktabsharif.achareh.repository;

import ir.maktabsharif.achareh.entity.Score;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ScoreJpaRepository extends JpaRepository<Score,Long> {
//    @Query("SELECT SUM(s.range) FROM Score s WHERE s.user.id = :userId")
//    Long sumAmountsByUserId(@Param("userId") Long userId);
}
