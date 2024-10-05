package ir.maktabsharif.achareh.controller;

import io.swagger.v3.oas.annotations.Operation;
import ir.maktabsharif.achareh.dto.order.OrderRequestDto;
import ir.maktabsharif.achareh.dto.order.OrderResponseDto;
import ir.maktabsharif.achareh.service.orderService.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/order")
public class OrderController {
    private final OrderService orderService;
    @PostMapping
    public ResponseEntity<OrderResponseDto> save(@Valid @RequestBody OrderRequestDto orderRequestDto) {

        return ResponseEntity.ok(orderService.save(orderRequestDto));
    }
    @GetMapping("/{subDutyId}")
    public ResponseEntity<List<OrderResponseDto>> getOrdersBySubDuty(@PathVariable Long subDutyId) {
       ;
        return ResponseEntity.ok( orderService.getOrdersBySubDutyId(subDutyId));
    }

    @PostMapping("/{orderId}")
    @Operation(summary = "change order status to starting with order id")
    public ResponseEntity<String> changeOrderStatusToStarting(@PathVariable Long orderId) {
          orderService.changeOrderStatusToStarting(orderId);
         return ResponseEntity.ok("change.status.successfully");
    }

}
