package ir.maktabsharif.achareh.repository;

import ir.maktabsharif.achareh.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleJpaRepository extends JpaRepository<Role,Long> {
    Optional<Role> findByName(String name);
}
