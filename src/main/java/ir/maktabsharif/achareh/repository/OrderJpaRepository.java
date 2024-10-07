package ir.maktabsharif.achareh.repository;

import ir.maktabsharif.achareh.entity.Order;
import ir.maktabsharif.achareh.enums.OrderStatusEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderJpaRepository extends JpaRepository<Order, Long> {
    List<Order> findBySubDutyIdAndStatusIn(Long subDutyId, List<OrderStatusEnum> statuses);
}
