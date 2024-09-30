package ir.maktabsharif.achareh.repository;

import ir.maktabsharif.achareh.entity.Duty;
import ir.maktabsharif.achareh.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DutyJpaRepository extends JpaRepository<Duty,Long> {
    Optional<Duty> findByName(String name);
}
