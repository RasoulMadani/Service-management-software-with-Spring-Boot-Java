package ir.maktabsharif.achareh.repository;

import ir.maktabsharif.achareh.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressJpaRepository extends JpaRepository<Address,Long> {
}
