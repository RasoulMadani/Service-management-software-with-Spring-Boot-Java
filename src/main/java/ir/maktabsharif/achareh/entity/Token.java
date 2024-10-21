package ir.maktabsharif.achareh.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


import java.time.LocalDateTime;

@Getter
@Setter
@Entity
public class Token extends BaseEntity<Long>{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // توکن فعال‌سازی
    @Column(nullable = false, unique = true)
    private String token;

    // ارتباط با موجودیت User
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, name = "user_id")
    private User user;

    // تاریخ انقضای توکن
    @Column(nullable = false)
    private LocalDateTime expiryDate;

    public Token() {}

    public Token(String token, User user, LocalDateTime expiryDate) {
        this.token = token;
        this.user = user;
        this.expiryDate = expiryDate;
    }

}