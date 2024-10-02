package ir.maktabsharif.achareh.controller;

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
    @PostMapping("/{subDutyId}")
    public ResponseEntity<List<OrderResponseDto>> getOrdersBySubDuty(@PathVariable Long subDutyId) {
       ;
        return ResponseEntity.ok( orderService.getOrdersBySubDutyId(subDutyId));
    }
}
