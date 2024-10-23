package ir.maktabsharif.achareh.dto.manager;

import ir.maktabsharif.achareh.enums.OrderStatusEnum;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
public class ReportUserDTO {
    private Long userId; // شناسه کاربر
    private Long orderCount; // تعداد سفارش‌ها (در صورت نیاز به شمارش)

    private LocalDate orderDate; // تاریخ سفارش
    private LocalTime orderTime; // زمان سفارش
    private Double suggestionPrice; // قیمت پیشنهاد شده
    private OrderStatusEnum orderStatus; // وضعیت سفارش

    // سازنده برای حالت شمارش سفارش‌ها
    public ReportUserDTO(Long userId, Long orderCount) {
        this.userId = userId;
        this.orderCount = orderCount;
    }

    // سازنده برای جزئیات سفارش
    public ReportUserDTO(LocalDate orderDate, LocalTime orderTime, Double suggestionPrice, OrderStatusEnum orderStatus) {
        this.orderDate = orderDate;
        this.orderTime = orderTime;
        this.suggestionPrice = suggestionPrice;
        this.orderStatus = orderStatus;
    }
}
