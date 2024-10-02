package ir.maktabsharif.achareh.service.orderService;

import ir.maktabsharif.achareh.dto.order.OrderRequestDto;
import ir.maktabsharif.achareh.dto.order.OrderResponseDto;

public interface OrderService {
    OrderResponseDto save(OrderRequestDto orderRequestDto);
}
