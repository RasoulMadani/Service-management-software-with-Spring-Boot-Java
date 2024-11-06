package ir.maktabsharif.achareh.controller;

import io.swagger.v3.oas.annotations.Operation;
import ir.maktabsharif.achareh.dto.order.OrderCommentRequestDto;
import ir.maktabsharif.achareh.dto.order.OrderCommentResponseDTO;
import ir.maktabsharif.achareh.dto.order.OrderRequestDto;
import ir.maktabsharif.achareh.dto.order.OrderResponseDto;
import ir.maktabsharif.achareh.service.orderService.OrderService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/order")
public class OrderController {
    private final OrderService orderService;
    @PostMapping
    @Operation(summary = "save order")
    @PreAuthorize("hasAuthority('ADD ORDER')")
    public ResponseEntity<OrderResponseDto> save(@Valid @RequestBody OrderRequestDto orderRequestDto) {

        return ResponseEntity.ok(orderService.save(orderRequestDto));
    }
    @GetMapping("/{subDutyId}")
    @PreAuthorize("hasAuthority('GET ORDERS')")
    @Operation(summary = "get orders this exist in subDuty")
    public ResponseEntity<List<OrderResponseDto>> getOrdersBySubDuty(@PathVariable Long subDutyId) {

        return ResponseEntity.ok( orderService.getOrdersBySubDutyId(subDutyId));
    }

    @PostMapping("/starting/{orderId}")
    @PreAuthorize("hasAuthority('EDIT ORDER')")
    @Operation(summary = "change order status to starting with order id")
    public ResponseEntity<String> changeOrderStatusToStarting(@PathVariable Long orderId) {
          orderService.changeOrderStatusToStarting(orderId);
         return ResponseEntity.ok("change.status.successfully");
    }
    @PostMapping("/score/{orderId}/{range}")
    @PreAuthorize("hasAuthority('ADD SCORE TO ORDER')")
    @Operation(summary = "save score for order")
    public ResponseEntity<String> addScoreToOrder(@PathVariable Double range, @PathVariable Long orderId) {
        if (range < 1.0 || range > 5.0) {
            throw new IllegalArgumentException("range.must.be.between.1.0_and_5.0");
        }

        orderService.addScoreToOrder(orderId,range);
        return ResponseEntity.ok("score.add.successfully");
    }

    @PostMapping("/comment")
    @PreAuthorize("hasAuthority('ADD COMMENT TO ORDER')")
    @Operation(summary = "add comment to order")
    public ResponseEntity<OrderCommentResponseDTO> addCommentToOrder(@Valid @RequestBody OrderCommentRequestDto orderCommentRequestDto) {

        return ResponseEntity.ok(orderService.addCommentToOrder(orderCommentRequestDto));
    }

    @PostMapping("/performed/{orderId}")
    @PreAuthorize("hasAuthority('EDIT ORDER')")
    @Operation(summary = "change order status to performed with order id")
    public ResponseEntity<String> changeOrderStatusToPerformed(@PathVariable Long orderId) {
        orderService.changeOrderStatusToPerformed(orderId);
        return ResponseEntity.ok("change.status.successfully");
    }

}
