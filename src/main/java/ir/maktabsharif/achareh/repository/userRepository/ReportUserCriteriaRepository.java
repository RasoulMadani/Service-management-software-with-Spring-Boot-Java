package ir.maktabsharif.achareh.repository.userRepository;

import ir.maktabsharif.achareh.dto.manager.OrderDetailsDTO;
import ir.maktabsharif.achareh.dto.manager.ReportSubDutyUserDTO;
import ir.maktabsharif.achareh.dto.manager.ReportUserDTO;
import ir.maktabsharif.achareh.dto.manager.UserOrderCountDTO;
import ir.maktabsharif.achareh.entity.Order;
import ir.maktabsharif.achareh.entity.*;
import ir.maktabsharif.achareh.enums.OrderStatusEnum;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.*;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Component
public class ReportUserCriteriaRepository {

    @PersistenceContext
    private EntityManager entityManager;

    public List<?> reportUsersWithCriteria(
            Long user_id,
            LocalDate startDateOrder,
            LocalDate endDateOrder,
            LocalDate registrationUserStartDate,
            LocalDate registrationUserEndDate,
            OrderStatusEnum orderStatus,
            boolean countOrder,
            boolean countPerformedOrder
    ) {

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();

        if (countOrder) {
            // ساخت کوئری برای شمارش سفارش‌ها و برگرداندن DTO مربوطه
            CriteriaQuery<UserOrderCountDTO> criteriaQuery = criteriaBuilder.createQuery(UserOrderCountDTO.class);
            Root<Order> root = criteriaQuery.from(Order.class);
            Join<Order, User> userJoin = root.join("user", JoinType.LEFT);

            List<Predicate> predicates = buildPredicates(criteriaBuilder, root, userJoin,user_id, startDateOrder, endDateOrder,registrationUserStartDate,registrationUserEndDate, orderStatus);
            criteriaQuery.where(criteriaBuilder.and(predicates.toArray(new Predicate[0])));

            criteriaQuery.multiselect(
                    userJoin.get("id"),
                    criteriaBuilder.count(root)
            );
            criteriaQuery.groupBy(userJoin.get("id"));
            criteriaQuery.orderBy(criteriaBuilder.desc(userJoin.get("id")));

            // اجرای کوئری و برگرداندن نتیجه
            return entityManager.createQuery(criteriaQuery).getResultList();

        } else {
            // ساخت کوئری برای جزئیات سفارش‌ها و برگرداندن DTO مربوطه
            CriteriaQuery<OrderDetailsDTO> criteriaQuery = criteriaBuilder.createQuery(OrderDetailsDTO.class);
            Root<Order> root = criteriaQuery.from(Order.class);
            Join<Order, User> userJoin = root.join("user", JoinType.LEFT);

            List<Predicate> predicates = buildPredicates(criteriaBuilder, root,userJoin, user_id, startDateOrder, endDateOrder,registrationUserStartDate,registrationUserEndDate, orderStatus);
            criteriaQuery.where(criteriaBuilder.and(predicates.toArray(new Predicate[0])));

            criteriaQuery.multiselect(
                    userJoin.get("id"),
                    root.get("date"),
                    root.get("time"),
                    root.get("suggestionPrice"),
                    root.get("status")
            );

            // اجرای کوئری و برگرداندن نتیجه
            return entityManager.createQuery(criteriaQuery).getResultList();
        }
    }

    private List<Predicate> buildPredicates(CriteriaBuilder criteriaBuilder, Root<Order> root,Join<Order, User> userJoin, Long user_id, LocalDate startDate, LocalDate endDate, LocalDate registrationStartDate, LocalDate registrationEndDate, OrderStatusEnum orderStatus) {
        List<Predicate> predicates = new ArrayList<>();

        if (user_id != null) {
            predicates.add(criteriaBuilder.equal(root.get("user"), new User(user_id)));
        }

        if (startDate != null && endDate != null) {
            predicates.add(criteriaBuilder.between(root.get("date"), startDate, endDate));
        } else if (startDate != null) {
            predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("date"), startDate));
        } else if (endDate != null) {
            predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("date"), endDate));
        }

        if (orderStatus != null) {
            predicates.add(criteriaBuilder.equal(root.get("status"), orderStatus));
        }

        // شرط تاریخ ثبت‌نام کاربر
        if (registrationStartDate != null && registrationEndDate != null) {
            predicates.add(criteriaBuilder.between(userJoin.get("creationDate"), registrationStartDate, registrationEndDate));
        } else if (registrationStartDate != null) {
            predicates.add(criteriaBuilder.greaterThanOrEqualTo(userJoin.get("creationDate"), registrationStartDate));
        } else if (registrationEndDate != null) {
            predicates.add(criteriaBuilder.lessThanOrEqualTo(userJoin.get("creationDate"), registrationEndDate));
        }

        return predicates;
    }
}