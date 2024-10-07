package ir.maktabsharif.achareh.repository.bankRepository;

import ir.maktabsharif.achareh.entity.bank.BankUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BankUserRepository extends JpaRepository<BankUser,Long> {
}
