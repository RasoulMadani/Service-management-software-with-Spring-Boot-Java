package ir.maktabsharif.achareh.service.orderService;

import ir.maktabsharif.achareh.dto.order.OrderRequestDto;
import ir.maktabsharif.achareh.dto.order.OrderResponseDto;
import ir.maktabsharif.achareh.entity.*;
import ir.maktabsharif.achareh.enums.StatusOrderEnum;
import ir.maktabsharif.achareh.exception.RuleException;
import ir.maktabsharif.achareh.repository.OrderJpaRepository;
import ir.maktabsharif.achareh.repository.SubDutyJpaRepository;
import ir.maktabsharif.achareh.repository.UserJpaRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderJpaRepository orderJpaRepository;
    private final SubDutyJpaRepository subDutyJpaRepository;
    private final UserJpaRepository userJpaRepository;

    @Override
    @Transactional
    public OrderResponseDto save(OrderRequestDto orderRequestDto) {

        User findUser = userJpaRepository.findById(orderRequestDto.user_id()).orElseThrow(() -> new RuleException("user.not.found"));

        SubDuty subDuy = subDutyJpaRepository.findById(orderRequestDto.sub_duty_id()).orElseThrow(() -> new RuleException("sub_duty.not.found"));

        if (subDuy.getBase_price() > orderRequestDto.suggestionPrice())
            throw new RuleException("suggestion_price.smaller.than.base_price");
        Address address = new Address(orderRequestDto.province(), orderRequestDto.city(), orderRequestDto.street(), orderRequestDto.addressDetails());

        Order order1 = Order.builder().address(address).suggestionPrice(orderRequestDto.suggestionPrice()).date(orderRequestDto.date()).time(orderRequestDto.time()).status(StatusOrderEnum.WAITING).subDuty(subDuy).user(findUser).description(orderRequestDto.description()).build();
        Order savedOrder = orderJpaRepository.save(order1);

        return new OrderResponseDto(savedOrder.getId(), findUser.getId(), subDuy.getId(), savedOrder.getSuggestionPrice(), savedOrder.getDescription(), address.getProvince(), address.getCity(), address.getStreet(), address.getDetails(), savedOrder.getDate(), savedOrder.getTime());
    }

    @Override
    public List<OrderResponseDto> getOrdersBySubDutyId(Long subDutyId) {
        List<StatusOrderEnum> statuses = List.of(StatusOrderEnum.WAITING, StatusOrderEnum.SELECT);
        List<Order> orders = orderJpaRepository.findBySubDutyIdAndStatusIn(subDutyId,statuses);
        return this.getAllOrdersDto(orders);

    }

    @Override
    public void changeOrderStatusToStarting(Long orderId) {
        Order order =
                orderJpaRepository.findById(orderId)
                        .orElseThrow(() -> new RuleException("order.not.found"));
        order.setStatus(StatusOrderEnum.STARTING);
        orderJpaRepository.save(order);
    }

    public List<OrderResponseDto> getAllOrdersDto(List<Order> orders) {
        return orders.stream().map(this::convertToOrderResponseDto).collect(Collectors.toList());
    }

    private OrderResponseDto convertToOrderResponseDto(Order order) {
        return new OrderResponseDto(
                order.getId(),
                order.getUser().getId(),
                order.getSubDuty().getId(),
                order.getSuggestionPrice(),
                order.getDescription(),
                order.getAddress().getProvince(),
                order.getAddress().getCity(),
                order.getAddress().getStreet(),
                order.getAddress().getDetails(),
                order.getDate(),
                order.getTime()
        );
    }
}
