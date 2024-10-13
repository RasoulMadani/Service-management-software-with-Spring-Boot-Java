package ir.maktabsharif.achareh.entity;


import ir.maktabsharif.achareh.enums.RoleUserEnum;
import ir.maktabsharif.achareh.enums.StatusUserEnum;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
@Entity
@Table(name = "users")
@Builder
@AllArgsConstructor
public class User extends BaseEntity<Long> {

    public User() {
    }

    @NotEmpty(message = "name cannot be empty")
    @NotNull(message = "name cannot be null")
    @Size(min = 3, max = 15, message = "name must be between 3 and 15 characters")
    private String name;

    @NotEmpty(message = "username cannot be empty")
    @NotNull(message = "username cannot be null")
    @Column(unique = true, nullable = false)
    private String username;

    @NotEmpty(message = "email cannot be empty")
    @NotNull(message = "email cannot be null")
    @Column(unique = true, nullable = false)
    private String email;

    @NotNull(message = "Password cannot be null")
    @Size(min = 8, message = "Password must be at least 8 characters long")
    @Pattern(
            regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{8,}$",
            message = "Password must contain at least one digit, one lowercase letter, one uppercase letter, and one special character"
    )
    private String password;

    private String image;

    @Enumerated(EnumType.STRING)
    private RoleUserEnum role = RoleUserEnum.CUSTOMER;

    @Enumerated(EnumType.STRING)
    private StatusUserEnum status = StatusUserEnum.NEW_USER;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(

            name = "sub_duty_users", // نام جدول واسط
            joinColumns = @JoinColumn(name = "user_id"), // کلید مربوط به user
            inverseJoinColumns = @JoinColumn(name = "sub_duty_id") // کلید مربوط به sub_duty
    )
    @Builder.Default
    private List<SubDuty> sub_duties = new ArrayList<>();


    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "address_id", referencedColumnName = "id")
    private Address address;

    @OneToOne(fetch = FetchType.LAZY,mappedBy = "user", cascade = CascadeType.ALL)
    private Score score;

    @OneToMany(fetch = FetchType.LAZY,targetEntity = Order.class , mappedBy="user")
    @Builder.Default
    private List<Order> orders = new ArrayList<>();



    public void addSubDuty(SubDuty subDuty) {
        this.sub_duties.add(subDuty);
        subDuty.getUsers().add(this);
    }

    public void removeSubDuty(SubDuty subDuty) {
        this.sub_duties.remove(subDuty);
        subDuty.getUsers().remove(this);
    }
}
