package ir.maktabsharif.achareh.service.orderService;

import ir.maktabsharif.achareh.dto.order.OrderRequestDto;
import ir.maktabsharif.achareh.dto.order.OrderResponseDto;

import java.util.List;

public interface OrderService {
    OrderResponseDto save(OrderRequestDto orderRequestDto);

    List<OrderResponseDto> getOrdersBySubDutyId(Long subDutyId);
    void changeOrderStatusToStarting(Long orderId);
}
