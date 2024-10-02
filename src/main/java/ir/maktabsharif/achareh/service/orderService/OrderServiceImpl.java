package ir.maktabsharif.achareh.service.orderService;

import ir.maktabsharif.achareh.dto.order.OrderRequestDto;
import ir.maktabsharif.achareh.dto.order.OrderResponseDto;
import ir.maktabsharif.achareh.dto.subDuty.SubDutyRequestDto;
import ir.maktabsharif.achareh.dto.subDuty.SubDutyResponseDto;
import ir.maktabsharif.achareh.entity.*;
import ir.maktabsharif.achareh.enums.StatusSuggestionEnum;
import ir.maktabsharif.achareh.exception.RuleException;
import ir.maktabsharif.achareh.repository.OrderJpaRepository;
import ir.maktabsharif.achareh.repository.SubDutyJpaRepository;
import ir.maktabsharif.achareh.repository.UserJpaRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderJpaRepository orderJpaRepository;
    private final SubDutyJpaRepository subDutyJpaRepository;
    private final UserJpaRepository userJpaRepository;

    @Override
    @Transactional
    public OrderResponseDto save(OrderRequestDto orderRequestDto) {

        User findUser =
                userJpaRepository.findById(orderRequestDto.user_id())
                        .orElseThrow(() -> new RuleException("user.not.found"));

        SubDuty subDuy =
                subDutyJpaRepository.findById(orderRequestDto.sub_duty_id())
                        .orElseThrow(() -> new RuleException("sub_duty.not.found"));

        if (subDuy.getBase_price() > orderRequestDto.suggestionPrice())
            throw new RuleException("suggestion_price.smaller.than.base_price");
        Address address = new Address(orderRequestDto.province(), orderRequestDto.city(), orderRequestDto.street(), orderRequestDto.addressDetails());

        Order order1 = Order.builder()
                .address(address)
                .suggestionPrice(orderRequestDto.suggestionPrice())
                .date(orderRequestDto.date())
                .time(orderRequestDto.time())
                .status(StatusSuggestionEnum.WAITING_SUGGESTION)
                .subDuty(subDuy)
                .user(findUser)
                .description(orderRequestDto.description())
                .build();
        Order savedOrder = orderJpaRepository.save(order1);

        return new OrderResponseDto(
                savedOrder.getId(),
                findUser.getId(),
                subDuy.getId(),
                savedOrder.getSuggestionPrice(),
                savedOrder.getDescription(),
                address.getProvince(),
                address.getCity(),
                address.getStreet(),
                address.getDetails(),
                savedOrder.getDate(),
                savedOrder.getTime()
        );
    }
}
