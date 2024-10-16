package ir.maktabsharif.achareh.entity;


import ir.maktabsharif.achareh.enums.OrderStatusEnum;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "orders")
@Builder
public class Order extends BaseEntity<Long>{
    public Order(Long aLong) {
        super.setId(aLong);
    }
    private Double suggestionPrice;


    @NotEmpty(message = "description cannot be empty")
    @NotNull(message = "description cannot be null")
    @Size(min = 40, message = "description must be more than 40 characters")
    private String description;


    @Enumerated(EnumType.STRING)
    private OrderStatusEnum status;


    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;



    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id", referencedColumnName = "id")
    private Address address;

    @FutureOrPresent(message = "Event date must in the future")
    private LocalDate date;

    private LocalTime time;

    @OneToOne
    @JoinColumn(name = "score_id", referencedColumnName = "id")
    private Score score;

    @OneToOne
    @JoinColumn(name = "suggestion_id", referencedColumnName = "id")
    private Suggestion suggestion;

    @ManyToOne
    @JoinColumn(name = "sub_duty_id", referencedColumnName = "id")
    private SubDuty subDuty;

    @OneToMany(targetEntity = OrderComment.class , mappedBy="order")
    @Builder.Default
    private List<OrderComment> comments = new ArrayList<>();

    @OneToMany(targetEntity = Suggestion.class , mappedBy="order")
    @Builder.Default
    private List<Suggestion> suggestions = new ArrayList<>();


}
