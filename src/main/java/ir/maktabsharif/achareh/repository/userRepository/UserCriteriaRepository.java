package ir.maktabsharif.achareh.repository.userRepository;

import ir.maktabsharif.achareh.dto.user.UserDTO;
import ir.maktabsharif.achareh.entity.User;
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

    public List<UserDTO> findUsersWithCriteria(String name, String username, String email) {
        // ایجاد CriteriaBuilder
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();

        // ایجاد CriteriaQuery برای موجودیت User
        CriteriaQuery<UserDTO> criteriaQuery = criteriaBuilder.createQuery(UserDTO.class);

        // ایجاد Root برای جداول اصلی
        Root<User> root = criteriaQuery.from(User.class);

        // انتخاب فقط فیلدهای خاص
//        criteriaQuery.multiselect(root.get("name"), root.get("username"), root.get("email"));

        // Join برای بارگذاری Lazy (در صورت نیاز)
//        root.fetch("orders", JoinType.LEFT);

        // انتخاب فقط فیلدهای خاص و ساخت DTO
        criteriaQuery.select(criteriaBuilder.construct(
                UserDTO.class,
                root.get("name"),
                root.get("username"),
                root.get("email")
        ));

        // فیلترهای پویا
        List<Predicate> predicates = new ArrayList<>();

        if (name != null && !name.isEmpty()) {
            predicates.add(criteriaBuilder.equal(root.get("name"), name));
        }

        if (username != null) {
            predicates.add(criteriaBuilder.equal(root.get("username"), username));
        }

        if (email != null && !email.isEmpty()) {
            predicates.add(criteriaBuilder.equal(root.get("email"), email));
        }

        // اضافه کردن شروط به کوئری
        criteriaQuery.where(criteriaBuilder.and(predicates.toArray(new Predicate[0])));

        // اجرای کوئری و برگرداندن نتیجه
        return entityManager.createQuery(criteriaQuery).getResultList();
    }
}