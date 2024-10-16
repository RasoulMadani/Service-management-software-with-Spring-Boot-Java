package ir.maktabsharif.achareh.service.orderService;

import ir.maktabsharif.achareh.dto.order.OrderCommentRequestDto;
import ir.maktabsharif.achareh.dto.order.OrderCommentResponseDTO;
import ir.maktabsharif.achareh.dto.order.OrderRequestDto;
import ir.maktabsharif.achareh.dto.order.OrderResponseDto;

import java.util.List;

public interface OrderService {
    OrderResponseDto save(OrderRequestDto orderRequestDto);

    List<OrderResponseDto> getOrdersBySubDutyId(Long subDutyId);
    void changeOrderStatusToStarting(Long orderId);
    void changeOrderStatusToPerformed(Long orderId);

    void addScoreToOrder(Long orderId,Double range);
    OrderCommentResponseDTO addCommentToOrder(OrderCommentRequestDto orderCommentRequestDto);
}
