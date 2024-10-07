package ir.maktabsharif.achareh.repository.bankRepository;

import ir.maktabsharif.achareh.entity.bank.BankTransaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BankTransactionRepository extends JpaRepository<BankTransaction,Long> {
}
