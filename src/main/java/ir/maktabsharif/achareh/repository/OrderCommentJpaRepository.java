package ir.maktabsharif.achareh.repository;

import ir.maktabsharif.achareh.entity.OrderComment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderCommentJpaRepository extends JpaRepository<OrderComment,Long> {
}
