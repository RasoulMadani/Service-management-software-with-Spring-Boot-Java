package ir.maktabsharif.achareh.repository;

import ir.maktabsharif.achareh.entity.Order;
import ir.maktabsharif.achareh.entity.Suggestion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SuggestionJpaRepository extends JpaRepository<Suggestion,Long> {
    List<Suggestion> findAllByOrderIdOrderBySuggestionPriceAsc(Long orderId);
}
