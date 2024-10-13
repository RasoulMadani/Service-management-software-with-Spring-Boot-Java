package ir.maktabsharif.achareh.repository;

import ir.maktabsharif.achareh.entity.Financial;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FinancialJpaRepository extends JpaRepository<Financial,Long> {
}
