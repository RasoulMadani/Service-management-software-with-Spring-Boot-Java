package ir.maktabsharif.achareh.entity;

import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;

public class Financial extends BaseEntity<Long>{
    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;
    private Double amount;
}

