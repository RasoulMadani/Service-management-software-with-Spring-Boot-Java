package ir.maktabsharif.achareh.service.orderService;

import ir.maktabsharif.achareh.dto.order.OrderCommentRequestDto;
import ir.maktabsharif.achareh.dto.order.OrderCommentResponseDTO;
import ir.maktabsharif.achareh.dto.order.OrderRequestDto;
import ir.maktabsharif.achareh.dto.order.OrderResponseDto;
import ir.maktabsharif.achareh.entity.*;
import ir.maktabsharif.achareh.enums.OrderStatusEnum;
import ir.maktabsharif.achareh.exception.RuleException;
import ir.maktabsharif.achareh.repository.OrderCommentJpaRepository;
import ir.maktabsharif.achareh.repository.OrderJpaRepository;
import ir.maktabsharif.achareh.repository.ScoreJpaRepository;
import ir.maktabsharif.achareh.repository.SubDutyJpaRepository;
import ir.maktabsharif.achareh.repository.userRepository.UserJpaRepository;
import ir.maktabsharif.achareh.service.UserService.CustomUserDetails;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.*;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderJpaRepository orderJpaRepository;
    private final SubDutyJpaRepository subDutyJpaRepository;
    private final UserJpaRepository userJpaRepository;
    private final ScoreJpaRepository scoreJpaRepository;
    private final OrderCommentJpaRepository orderCommentJpaRepository;

    @Override
    @Transactional
    public OrderResponseDto save(OrderRequestDto orderRequestDto) {

        User findUser = getCurrentUser();

        SubDuty subDuy = subDutyJpaRepository
                .findById(orderRequestDto.sub_duty_id()).orElseThrow(() -> new RuleException("sub_duty.not.found"));

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
    @Transactional
    public void changeOrderStatusToStarting(Long orderId) {
        Order order =
                orderJpaRepository.findById(orderId)
                        .orElseThrow(() -> new RuleException("order.not.found"));

//        User user = getCurrentUser();

//        if(!Objects.equals(order.getUser().getId(), user.getId()))throw new RuleException("this.order.is.for.other.user");

        Suggestion suggestion = order.getSuggestion();

        Optional<Suggestion> suggestionOptional = Optional.ofNullable(order.getSuggestion());
        suggestionOptional.orElseThrow(() -> new RuleException("suggestion.not.accepted.for.this.order"));


        if (suggestion.getStartDate().isAfter(LocalDate.now()))
            throw new RuleException("start.date.is.after.now.date");

        order.setStatus(OrderStatusEnum.STARTING);
        orderJpaRepository.save(order);
    }

    @Override
    @Transactional
    public void changeOrderStatusToPerformed(Long orderId) {
        Order order =
                orderJpaRepository.findById(orderId)
                        .orElseThrow(() -> new RuleException("order.not.found"));

        if (isOrderPerformed(order)) throw new RuleException("order.before.performed");

        Optional<Suggestion> suggestionOptional = Optional.ofNullable(order.getSuggestion());
        suggestionOptional.orElseThrow(() -> new RuleException("suggestion.not.accepted.for.this.order"));

        Suggestion suggestion = suggestionOptional.get();

        if (suggestion.getStartDate().isAfter(LocalDate.now()))
            throw new RuleException("start.date.is.after.now.date");

        Duration duration = getDuration(suggestion);

        long hoursOfBetweenStartAndEnd = duration.toHours();
        long differentHorse = hoursOfBetweenStartAndEnd - suggestion.getDurationOfWork();


        if (differentHorse > 0) {
            Score score = new Score((-(0.0 + differentHorse)), suggestion.getUser(),order.getId(), "time late");
            scoreJpaRepository.save(score);
        }

        order.setStatus(OrderStatusEnum.PERFORMED);
        orderJpaRepository.save(order);
    }

    @Override
    @Transactional
    public void addScoreToOrder(Long orderId, Double range) {
        Order order =
                orderJpaRepository.findById(orderId)
                        .orElseThrow(() -> new RuleException("order.not.found"));

        if (!isOrderPerformed(order)) {
            throw new RuleException("order.not.performed");
        }
        Optional<Suggestion> suggestionOptional = Optional.ofNullable(order.getSuggestion());
        suggestionOptional.orElseThrow(() -> new RuleException("suggestion.not.accepted.for.this.order"));

        Score score = new Score(range,suggestionOptional.get().getUser(), order.getId(), "performed order");
        scoreJpaRepository.save(score);

    }

    @Override
    @Transactional
    public OrderCommentResponseDTO addCommentToOrder(OrderCommentRequestDto orderCommentRequestDto) {
        boolean order =
                orderJpaRepository.existsById(orderCommentRequestDto.order_id());
        if(!order) throw  new RuleException("order.not.found");

        OrderComment orderComment = new OrderComment(orderCommentRequestDto.message(), new Order(orderCommentRequestDto.order_id()));
        orderComment = orderCommentJpaRepository.save(orderComment);

        return new OrderCommentResponseDTO(orderComment.getMessage(), orderComment.getOrder().getId());
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
    private User getCurrentUser() {
        CustomUserDetails customUserDetails =
                (CustomUserDetails)  SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        return customUserDetails.getUser();
    }
}
