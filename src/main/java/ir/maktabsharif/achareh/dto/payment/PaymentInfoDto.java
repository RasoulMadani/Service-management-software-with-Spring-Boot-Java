package ir.maktabsharif.achareh.dto.payment;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class PaymentInfoDto {
    private String cardholderName;
    private String cardNumber;
    private Integer expiryMonth;
    private Integer expiryYear;
    private Integer cvv;
    private Double amount;
    private Long order_id;
}
