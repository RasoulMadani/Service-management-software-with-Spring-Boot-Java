package ir.maktabsharif.achareh.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class OrderTransaction extends BaseEntity<Long>{
    @OneToOne
    @JoinColumn(name = "user_origin_id", referencedColumnName = "id")
    private User origin;

    @OneToOne
    @JoinColumn(name = "user_destination_id", referencedColumnName = "id")
    private User destination;

    @OneToOne
    @JoinColumn(name = "order_id", referencedColumnName = "id")
    private Order order;

    private double amount;

}
