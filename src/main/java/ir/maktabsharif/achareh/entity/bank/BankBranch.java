package ir.maktabsharif.achareh.entity.bank;

import ir.maktabsharif.achareh.entity.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Setter
@Getter
public class BankBranch extends BaseEntity<Long> {
    private String name;
    @ManyToOne
    @JoinColumn(name = "bank_id", referencedColumnName = "id")
    private Bank bank;
    @OneToMany(targetEntity = BankAccount.class , mappedBy="branch")
    private List<BankAccount> accounts = new ArrayList<>();

}
