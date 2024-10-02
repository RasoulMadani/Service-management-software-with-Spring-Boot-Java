package ir.maktabsharif.achareh.repository;

import ir.maktabsharif.achareh.entity.Order;
import ir.maktabsharif.achareh.enums.StatusSuggestionEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderJpaRepository extends JpaRepository<Order, Long> {
    List<Order> findBySubDutyIdAndStatusIn(Long subDutyId, List<StatusSuggestionEnum> statuses);
}
