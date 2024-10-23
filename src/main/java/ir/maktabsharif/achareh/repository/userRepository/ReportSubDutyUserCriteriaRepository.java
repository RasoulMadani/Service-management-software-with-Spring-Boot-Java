package ir.maktabsharif.achareh.repository.userRepository;

import ir.maktabsharif.achareh.dto.manager.ReportSubDutyUserDTO;
import ir.maktabsharif.achareh.dto.user.UserDTO;
import ir.maktabsharif.achareh.entity.*;
import ir.maktabsharif.achareh.entity.Order;
import ir.maktabsharif.achareh.enums.OrderStatusEnum;
import ir.maktabsharif.achareh.enums.RoleUserEnum;
import ir.maktabsharif.achareh.enums.StatusUserEnum;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.*;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Component
public class ReportSubDutyUserCriteriaRepository {

    @PersistenceContext
    private EntityManager entityManager;

    public List<ReportSubDutyUserDTO> findSubDutiesWithCriteria(
            Long user_id,
            LocalDate startDate,
            LocalDate endDate,
            OrderStatusEnum orderStatus,
            String subDutyName,
            String dutyName
    ) {

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();


        CriteriaQuery<ReportSubDutyUserDTO> criteriaQuery = criteriaBuilder.createQuery(ReportSubDutyUserDTO.class);


        Root<Order> root = criteriaQuery.from(Order.class);


        Join<Order, Suggestion> suggestion = root.join("suggestion", JoinType.LEFT);
//
        Join<Order, SubDuty> subDuty = root.join("subDuty", JoinType.LEFT);
//
        Join<SubDuty, Duty> duty = subDuty.join("duty", JoinType.LEFT);

        // انتخاب فقط فیلدهای خاص و ساخت DTO
        criteriaQuery.multiselect(
                root.get("date"),
                root.get("time"),
                suggestion.get("startDate"),
                suggestion.get("startTime"),
                suggestion.get("durationOfWork"),
                root.get("suggestionPrice"),
                suggestion.get("suggestionPrice"),
                root.get("status"),
                subDuty.get("name"),
                duty.get("name")
        );

        // فیلترهای پویا
        List<Predicate> predicates = new ArrayList<>();

        if (user_id != null) {
            predicates.add(criteriaBuilder.equal(root.get("user"), new User(user_id)));
        }

        if (startDate != null && endDate != null) {
            predicates.add(criteriaBuilder.between(root.get("date"), startDate,endDate));
        }else if(startDate != null){
            predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("date"), startDate));
        }else if(endDate != null){
            predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("date"), endDate));
        }


        if (orderStatus != null) {
            predicates.add(criteriaBuilder.equal(root.get("status"), orderStatus));
        }


        if (subDutyName != null) {
            predicates.add(criteriaBuilder.like(criteriaBuilder.lower(subDuty.get("name")),  "%" + subDutyName.toLowerCase() + "%"));
        }

        if (dutyName != null) {
            predicates.add(criteriaBuilder.like(criteriaBuilder.lower(duty.get("name")),  "%" + dutyName.toLowerCase() + "%"));
        }


        // اضافه کردن شروط به کوئری
        criteriaQuery.where(criteriaBuilder.and(predicates.toArray(new Predicate[0])));


        // اجرای کوئری و برگرداندن نتیجه
        return entityManager.createQuery(criteriaQuery).getResultList();
    }
}