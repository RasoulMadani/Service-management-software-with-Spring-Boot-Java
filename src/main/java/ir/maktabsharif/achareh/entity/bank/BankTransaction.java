package ir.maktabsharif.achareh.entity.bank;

import ir.maktabsharif.achareh.entity.BaseEntity;
import ir.maktabsharif.achareh.entity.Duty;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
public class BankTransaction extends BaseEntity<Long> {

    @ManyToOne
    @JoinColumn(name = "user_destination_id", referencedColumnName = "id")
    private BankUser destination;

    @ManyToOne
    @JoinColumn(name = "user_origin_id", referencedColumnName = "id")
    private BankUser origin;

    private Double amount;
}
