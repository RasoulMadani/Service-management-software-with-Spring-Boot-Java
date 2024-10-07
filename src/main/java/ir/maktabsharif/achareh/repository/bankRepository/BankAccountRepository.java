package ir.maktabsharif.achareh.repository.bankRepository;

import ir.maktabsharif.achareh.entity.bank.BankAccount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BankAccountRepository extends JpaRepository<BankAccount,Long> {
}
