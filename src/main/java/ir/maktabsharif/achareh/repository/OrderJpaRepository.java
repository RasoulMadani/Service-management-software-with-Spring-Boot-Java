package ir.maktabsharif.achareh.repository;

import ir.maktabsharif.achareh.dto.order.OrderWithIdDTO;
import ir.maktabsharif.achareh.entity.Order;
import ir.maktabsharif.achareh.enums.OrderStatusEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderJpaRepository extends JpaRepository<Order, Long> {
    List<Order> findBySubDutyIdAndStatusIn(Long subDutyId, List<OrderStatusEnum> statuses);
//    @Query("SELECT new ir.maktabsharif.achareh.dto.order.OrderWithIdDTO(o.id) FROM Order o WHERE o.id = :id")
//    Optional<OrderWithIdDTO> findOrderById(@Param("id") Long id);
}
