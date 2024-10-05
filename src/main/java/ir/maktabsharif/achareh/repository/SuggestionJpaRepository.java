package ir.maktabsharif.achareh.repository;

import ir.maktabsharif.achareh.entity.Suggestion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SuggestionJpaRepository extends JpaRepository<Suggestion,Long> {
}
