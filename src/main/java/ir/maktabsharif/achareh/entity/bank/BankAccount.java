package ir.maktabsharif.achareh.entity.bank;

import ir.maktabsharif.achareh.entity.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
public class BankAccount extends BaseEntity<Long> {
    private String accountNumber;
    @ManyToOne
    @JoinColumn(name = "branch_id", referencedColumnName = "id")
    private BankBranch branch;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private BankUser user;

    private double amount;

}
