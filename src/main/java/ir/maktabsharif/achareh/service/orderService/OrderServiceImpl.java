package ir.maktabsharif.achareh.service.orderService;

import ir.maktabsharif.achareh.dto.order.OrderRequestDto;
import ir.maktabsharif.achareh.dto.order.OrderResponseDto;
import ir.maktabsharif.achareh.entity.*;
import ir.maktabsharif.achareh.enums.OrderStatusEnum;
import ir.maktabsharif.achareh.exception.RuleException;
import ir.maktabsharif.achareh.repository.OrderJpaRepository;
import ir.maktabsharif.achareh.repository.ScoreRepository;
import ir.maktabsharif.achareh.repository.SubDutyJpaRepository;
import ir.maktabsharif.achareh.repository.userRepository.UserJpaRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.*;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderJpaRepository orderJpaRepository;
    private final SubDutyJpaRepository subDutyJpaRepository;
    private final UserJpaRepository userJpaRepository;
    private final ScoreRepository scoreRepository;

    @Override
    @Transactional
    public OrderResponseDto save(OrderRequestDto orderRequestDto) {

        User findUser = userJpaRepository.findById(orderRequestDto.user_id()).orElseThrow(() -> new RuleException("user.not.found"));

        SubDuty subDuy = subDutyJpaRepository.findById(orderRequestDto.sub_duty_id()).orElseThrow(() -> new RuleException("sub_duty.not.found"));

        if (subDuy.getBase_price() > orderRequestDto.suggestionPrice())
            throw new RuleException("suggestion_price.smaller.than.base_price");

        Address address = new Address(orderRequestDto.province(), orderRequestDto.city(), orderRequestDto.street(), orderRequestDto.addressDetails());

        Order order1 = Order.builder().address(address).suggestionPrice(orderRequestDto.suggestionPrice()).date(orderRequestDto.date()).time(orderRequestDto.time()).status(OrderStatusEnum.WAITING).subDuty(subDuy).user(findUser).description(orderRequestDto.description()).build();
        Order savedOrder = orderJpaRepository.save(order1);

        return new OrderResponseDto(savedOrder.getId(), findUser.getId(), subDuy.getId(), savedOrder.getSuggestionPrice(), savedOrder.getDescription(), address.getProvince(), address.getCity(), address.getStreet(), address.getDetails(), savedOrder.getDate(), savedOrder.getTime());
    }

    @Override
    public List<OrderResponseDto> getOrdersBySubDutyId(Long subDutyId) {
        List<OrderStatusEnum> statuses = List.of(OrderStatusEnum.WAITING, OrderStatusEnum.SELECT);
        List<Order> orders = orderJpaRepository.findBySubDutyIdAndStatusIn(subDutyId, statuses);
        return this.getAllOrdersDto(orders);

    }

    @Override
    public void changeOrderStatusToStarting(Long orderId) {
        Order order =
                orderJpaRepository.findById(orderId)
                        .orElseThrow(() -> new RuleException("order.not.found"));
        Suggestion suggestion = order.getSuggestion();

        Optional<Suggestion> suggestionOptional = Optional.ofNullable(order.getSuggestion());
        suggestionOptional.orElseThrow(() -> new RuleException("suggestion.not.accepted.for.this.order"));


        if (suggestion.getStartDate().isAfter(LocalDate.now()))
            throw new RuleException("start.date.is.after.now.date");

        order.setStatus(OrderStatusEnum.STARTING);
        orderJpaRepository.save(order);
    }

    @Override
    public void changeOrderStatusToPerformed(Long orderId) {
        Order order =
                orderJpaRepository.findById(orderId)
                        .orElseThrow(() -> new RuleException("order.not.found"));

        if(isOrderPerformed(order))throw new RuleException("order.before.performed");

        Optional<Suggestion> suggestionOptional = Optional.ofNullable(order.getSuggestion());
        suggestionOptional.orElseThrow(() -> new RuleException("suggestion.not.accepted.for.this.order"));

        Suggestion suggestion = suggestionOptional.get();

        if (suggestion.getStartDate().isAfter(LocalDate.now()))
            throw new RuleException("start.date.is.after.now.date");

        Duration duration = getDuration(suggestion);

        long hoursOfBetweenStartAndEnd = duration.toHours();
        long differentHorse = hoursOfBetweenStartAndEnd - suggestion.getDurationOfWork();


        if (differentHorse > 0) {
            Score score = new Score((- (0.0 + differentHorse)), suggestion.getUser(),order,"time late");
            scoreRepository.save(score);
        }

        order.setStatus(OrderStatusEnum.PERFORMED);
        orderJpaRepository.save(order);
    }

    @Override
    public void addScoreToOrder(Long orderId,Double range) {
        Order order = //ceae24
                orderJpaRepository.findById(orderId)
                        .orElseThrow(() -> new RuleException("order.not.found"));

        if (!isOrderPerformed(order)) {
            throw new RuleException("order.not.performed");
        }
        Optional<Suggestion> suggestionOptional = Optional.ofNullable(order.getSuggestion());
        suggestionOptional.orElseThrow(() -> new RuleException("suggestion.not.accepted.for.this.order"));

        Score score = new Score(range,suggestionOptional.get().getUser(),order,"performed order");
        scoreRepository.save(score);

    }

    private boolean isOrderPerformed(Order order) {
        return order.getStatus() == OrderStatusEnum.PERFORMED;
    }
    private Duration getDuration(Suggestion suggestion) {
        ZonedDateTime dateTimeNow = ZonedDateTime.now();

        ZonedDateTime startTime = ZonedDateTime.of(
                suggestion.getStartDate().getYear(),
                suggestion.getStartDate().getMonthValue(),
                suggestion.getStartDate().getDayOfMonth(),
                suggestion.getStartTime().getHour(),
                suggestion.getStartTime().getMinute(),
                0,
                0,
                ZoneId.of("Asia/Tehran")
        );

        ZonedDateTime endTime = ZonedDateTime.of(
                dateTimeNow.getYear(),
                dateTimeNow.getMonthValue(),
                dateTimeNow.getDayOfMonth(),
                dateTimeNow.getHour(),
                dateTimeNow.getMinute(),
                0,
                0,
                ZoneId.of("Asia/Tehran")
        );

        System.out.println(endTime);

        return Duration.between(startTime, endTime);
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
