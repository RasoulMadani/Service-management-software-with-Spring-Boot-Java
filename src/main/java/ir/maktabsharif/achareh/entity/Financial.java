package ir.maktabsharif.achareh.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class Financial extends BaseEntity<Long>{
    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;
    private Double amount;
}

