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

    @NotEmpty(message = "name cannot be empty")
    @NotNull(message = "name cannot be null")
    @Size(min = 3, max = 15, message = "name must be between 3 and 15 characters")
    private String name;

    private Long base_price;

    @Size(min = 10, max = 300, message = "name must be between 10 and 300 characters")
    private String definition;

    @ManyToMany(mappedBy = "sub_duties")
    private List<User> users = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "duty_id", referencedColumnName = "id")
    private Duty duty;
}
