package ir.maktabsharif.achareh.entity.bank;

import ir.maktabsharif.achareh.entity.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
public class BankCreditCard extends BaseEntity<Long> {
    @Size(min = 16,max = 16,message = "number.card.must.be.16.digits")
    private String cardNumber;
    @OneToOne
    @JoinColumn(name = "account_id", referencedColumnName = "id")
    private BankAccount account;
    @Min(value = 100, message = "CCV2 باید حداقل سه رقم باشد.")
    @Max(value = 999, message = "CCV2 باید حداکثر سه رقم باشد.")
    private Integer ccv2;
    @Min(value = 1000, message = "سال باید حداقل سه رقم باشد.")
    @Max(value = 9999, message = "سال باید حداکثر سه رقم باشد.")
    private Integer year;

    @Min(value = 1, message = "ماه باید حداقل یک باشد.")
    @Max(value = 12, message = "ماه باید حداکثر دوازده باشد.")
    private Integer month;
}
