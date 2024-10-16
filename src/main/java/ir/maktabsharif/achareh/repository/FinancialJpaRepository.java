package ir.maktabsharif.achareh.repository;

import ir.maktabsharif.achareh.entity.Financial;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FinancialJpaRepository extends JpaRepository<Financial,Long> {
    Optional<Financial> findByUserId(Long id);
}
