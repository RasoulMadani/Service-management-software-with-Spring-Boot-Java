package ir.maktabsharif.achareh.repository;

import ir.maktabsharif.achareh.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderJpaRepository extends JpaRepository<Order, Long> {
}
