package ir.maktabsharif.achareh.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "sub_duty")
@ToString
public class SubDuty extends BaseEntity<Long> {
    public SubDuty(String name, Double base_price, String definition,Duty duty) {
        this.name = name;
        this.base_price = base_price;
        this.definition = definition;
        this.duty = duty;
    }

    @NotEmpty(message = "name cannot be empty")
    @NotNull(message = "name cannot be null")
    @Size(min = 3, max = 15, message = "name must be between 3 and 15 characters")
    private String name;

    private Double base_price;

    @Size(min = 15, message = "{definition must be at least 15 characters}")
    private String definition;

    @ManyToMany(mappedBy = "sub_duties")
    private List<User> users = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "duty_id", referencedColumnName = "id")
    private Duty duty;

    @OneToMany(targetEntity = Order.class , mappedBy="subDuty")
    private List<Order> orders = new ArrayList<>();
}
