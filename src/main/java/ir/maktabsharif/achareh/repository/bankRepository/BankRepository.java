package ir.maktabsharif.achareh.repository.bankRepository;

import ir.maktabsharif.achareh.entity.bank.Bank;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BankRepository extends JpaRepository<Bank,Long> {
}
