package ir.maktabsharif.achareh.repository.userRepository;

import ir.maktabsharif.achareh.dto.user.UserDTO;
import ir.maktabsharif.achareh.entity.Duty;
import ir.maktabsharif.achareh.entity.Score;
import ir.maktabsharif.achareh.entity.SubDuty;
import ir.maktabsharif.achareh.entity.User;
import ir.maktabsharif.achareh.enums.OrderStatusEnum;
import ir.maktabsharif.achareh.enums.RoleUserEnum;
import ir.maktabsharif.achareh.enums.StatusUserEnum;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

@Component
public class UserCriteriaRepository {

    @PersistenceContext
    private EntityManager entityManager;

    public List<UserDTO> findUsersWithCriteria(String name,
                                               String username,
                                               String email,
                                               StatusUserEnum status,
                                               RoleUserEnum role,
                                               String subDutyName,
                                               String dutyName,
                                               boolean orderByScore
    ) {

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();


        CriteriaQuery<UserDTO> criteriaQuery = criteriaBuilder.createQuery(UserDTO.class);


        Root<User> root = criteriaQuery.from(User.class);


        Join<User, SubDuty> subDuties = root.join("sub_duties", JoinType.LEFT);
        Join<User, Score> userScoreJoin = root.join("score", JoinType.LEFT);
        Join<SubDuty, Duty> duty = subDuties.join("duty", JoinType.LEFT);

        // انتخاب فقط فیلدهای خاص و ساخت DTO
        criteriaQuery.multiselect(
                root.get("name"),
                root.get("username"),
                root.get("email"),
                root.get("status"),
                root.get("role"),
                subDuties.get("name"),
                duty.get("name"),
                userScoreJoin.get("range")
        );

        // فیلترهای پویا
        List<Predicate> predicates = new ArrayList<>();

        if (name != null && !name.isEmpty()) {
            predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("name")),  "%" + name.toLowerCase() + "%"));
        }

        if (username != null) {
            predicates.add(criteriaBuilder.equal(root.get("username"), username));
        }

        if (email != null && !email.isEmpty()) {
            predicates.add(criteriaBuilder.equal(root.get("email"), email));
        }
        if (status != null) {
            predicates.add(criteriaBuilder.equal(root.get("status"), status));
        }

        if (role != null) {
            predicates.add(criteriaBuilder.equal(root.get("role"), role));
        }


        if (subDutyName != null) {
            predicates.add(criteriaBuilder.like(criteriaBuilder.lower(subDuties.get("name")),  "%" + subDutyName.toLowerCase() + "%"));
        }

        if (dutyName != null) {
            predicates.add(criteriaBuilder.like(criteriaBuilder.lower(duty.get("name")),  "%" + dutyName.toLowerCase() + "%"));
        }


        // اضافه کردن شروط به کوئری
        criteriaQuery.where(criteriaBuilder.and(predicates.toArray(new Predicate[0])));

        if(orderByScore){
            criteriaQuery.orderBy(criteriaBuilder.desc(userScoreJoin.get("range")));
        }else {
            criteriaQuery.orderBy(criteriaBuilder.asc(userScoreJoin.get("range")));
        }


        // اجرای کوئری و برگرداندن نتیجه
        return entityManager.createQuery(criteriaQuery).getResultList();
    }
}