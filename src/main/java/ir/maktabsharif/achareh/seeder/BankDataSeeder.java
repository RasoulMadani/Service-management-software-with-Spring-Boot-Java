package ir.maktabsharif.achareh.seeder;

import ir.maktabsharif.achareh.entity.bank.*;
import ir.maktabsharif.achareh.repository.bankRepository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class BankDataSeeder implements CommandLineRunner {
    private final BankRepository bankRepository;
    private final BankBranchRepository bankBranchRepository;
    private final BankUserRepository bankUserRepository;
    private final BankAccountRepository bankAccountRepository;
    private final BankCreditCardRepository bankCreditCardRepository;
    private final BankTransactionRepository bankTransactionRepository;
    @Override
    public void run(String... args) throws Exception {
        if (bankRepository.count() == 0) {
            seedBankData();
        }

        if (bankUserRepository.count() == 0) {
            seedBankUsers();
        }

        if (bankAccountRepository.count() == 0) {
            seedBankAccounts();
        }

        if (bankCreditCardRepository.count() == 0) {
            seedCreditCards();
        }

        if (bankTransactionRepository.count() == 0) {
            seedTransactions();
        }
    }
    private void seedBankData() {
        Bank bank1 = Bank.builder().name("Bank One").code("B001").build();
        Bank bank2 = Bank.builder().name("Bank Two").code("B002").build();

        bankRepository.saveAll(List.of(bank1, bank2));

        BankBranch branch1 = new BankBranch();
        branch1.setName("Main Branch");
        branch1.setBank(bank1);

        BankBranch branch2 = new BankBranch();
        branch2.setName("Second Branch");
        branch2.setBank(bank2);

        bankBranchRepository.saveAll(List.of(branch1, branch2));
    }

    private void seedBankUsers() {
        BankUser user1 = new BankUser();
        user1.setName("John Doe");

        BankUser user2 = new BankUser();
        user2.setName("Jane Doe");

        bankUserRepository.saveAll(List.of(user1, user2));
    }

    private void seedBankAccounts() {
        BankBranch mainBranch = bankBranchRepository.findById(1L).orElseThrow();
        BankBranch secondBranch = bankBranchRepository.findById(2L).orElseThrow();

        BankUser user1 = bankUserRepository.findById(1L).orElseThrow();
        BankUser user2 = bankUserRepository.findById(2L).orElseThrow();

        BankAccount account1 = new BankAccount();
        account1.setAccountNumber("123456789");
        account1.setBranch(mainBranch);
        account1.setUser(user1);
        account1.setAmount(1000.0);

        BankAccount account2 = new BankAccount();
        account2.setAccountNumber("987654321");
        account2.setBranch(secondBranch);
        account2.setUser(user2);
        account2.setAmount(500.0);

        bankAccountRepository.saveAll(List.of(account1, account2));
    }

    private void seedCreditCards() {
        BankAccount account1 = bankAccountRepository.findById(1L).orElseThrow();

        BankCreditCard card1 = new BankCreditCard();
        card1.setCardNumber("1111222233334444");
        card1.setAccount(account1);
        card1.setCcv2(123);
        card1.setYear(2025);
        card1.setMonth(12);

        bankCreditCardRepository.save(card1);
    }

    private void seedTransactions() {
        BankUser user1 = bankUserRepository.findById(1L).orElseThrow();
        BankUser user2 = bankUserRepository.findById(2L).orElseThrow();

        BankTransaction transaction = new BankTransaction();
        transaction.setOrigin(user1);
        transaction.setDestination(user2);
        transaction.setAmount(250.0);

        bankTransactionRepository.save(transaction);
    }
}
