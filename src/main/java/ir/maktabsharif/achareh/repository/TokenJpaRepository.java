package ir.maktabsharif.achareh.repository;

import ir.maktabsharif.achareh.entity.Token;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TokenJpaRepository extends JpaRepository<Token, Long> {
    Token findByToken(String token);
}