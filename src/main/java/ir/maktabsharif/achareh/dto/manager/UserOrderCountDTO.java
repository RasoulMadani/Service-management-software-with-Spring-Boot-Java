package ir.maktabsharif.achareh.dto.manager;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserOrderCountDTO {
    private Long userId;
    private Long orderCount;

    public UserOrderCountDTO(Long userId, Long orderCount) {
        this.userId = userId;
        this.orderCount = orderCount;
    }

    // Getters و Setters
}

