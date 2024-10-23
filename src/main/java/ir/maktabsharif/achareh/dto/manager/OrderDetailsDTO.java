package ir.maktabsharif.achareh.dto.manager;


import ir.maktabsharif.achareh.enums.OrderStatusEnum;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;
@Setter
@Getter
public class OrderDetailsDTO {
    private Long user_id;
    private LocalDate date;
    private LocalTime time;
    private Double suggestionPrice;
    private OrderStatusEnum status;

    public OrderDetailsDTO(Long user_id,LocalDate date, LocalTime time, Double suggestionPrice, OrderStatusEnum status) {
        this.user_id = user_id;
        this.date = date;
        this.time = time;
        this.suggestionPrice = suggestionPrice;
        this.status = status;
    }

}