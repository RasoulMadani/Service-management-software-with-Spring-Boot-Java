package ir.maktabsharif.achareh.service.suggestionService;

import ir.maktabsharif.achareh.dto.suggesion.SuggestionRequestDto;
import ir.maktabsharif.achareh.dto.suggesion.SuggestionResponseDto;
import ir.maktabsharif.achareh.entity.*;
import ir.maktabsharif.achareh.enums.OrderStatusEnum;
import ir.maktabsharif.achareh.exception.RuleException;
import ir.maktabsharif.achareh.repository.OrderJpaRepository;
import ir.maktabsharif.achareh.repository.SuggestionJpaRepository;
import ir.maktabsharif.achareh.repository.userRepository.UserJpaRepository;
import ir.maktabsharif.achareh.service.UserService.CustomUserDetails;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SuggestionServiceImpl implements SuggestionService {
    private final SuggestionJpaRepository suggestionJpaRepository;
    private final UserJpaRepository userJpaRepository;
    private final OrderJpaRepository orderJpaRepository;

    @Override
    @Transactional
    public SuggestionResponseDto save(SuggestionRequestDto suggestionRequestDto) {
        CustomUserDetails customUserDetails =
                (CustomUserDetails)  SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        User findUser = customUserDetails.getUser();

        Order order =
                orderJpaRepository.findById(suggestionRequestDto.order_id())
                        .orElseThrow(() -> new RuleException("order.not.found"));

        boolean suggestionExist =
                suggestionJpaRepository
                        .existsByOrderIdAndUserId(order.getId(),findUser.getId());

        if(suggestionExist)throw new RuleException("you.suggested.this.order.before");

        if (order.getSubDuty().getBase_price() > suggestionRequestDto.suggestionPrice())
            throw new RuleException("suggestion_price.smaller.than.base_price");

        if (order.getDate().isAfter(suggestionRequestDto.date()))
            throw new RuleException("suggestion.startDate.must.be.after.order.startDate");

        Suggestion suggestion = Suggestion.builder()
                .suggestionPrice(suggestionRequestDto.suggestionPrice())
                .startDate(suggestionRequestDto.date())
                .startTime(suggestionRequestDto.time())
                .durationOfWork(suggestionRequestDto.durationOfWork())
                .order(order)
                .user(findUser)
                .build();

        Suggestion savedSuggestion = suggestionJpaRepository.save(suggestion);
        order.setStatus(OrderStatusEnum.SELECT);
        orderJpaRepository.save(order);

        return new SuggestionResponseDto(
                savedSuggestion.getId(),
                savedSuggestion.getUser().getId(),
                savedSuggestion.getOrder().getId(),
                savedSuggestion.getSuggestionPrice(),
                savedSuggestion.getStartDate(),
                savedSuggestion.getStartTime(),
                savedSuggestion.getDurationOfWork()
        );

    }

    @Override
    public List<SuggestionResponseDto> getAllByOrderId(Long orderId) {

        boolean order =
                orderJpaRepository.existsById(orderId);

        if(!order)throw  new RuleException("order.not.found");

        List<Suggestion> suggestions = suggestionJpaRepository.findAllByOrderIdOrderBySuggestionPriceAsc(orderId);

        return this.getAllSuggestionsDto(suggestions);
    }

    @Override
    public void acceptSuggestionWithId(Long suggestionId) {
        Suggestion  suggestion =
                suggestionJpaRepository.findById(suggestionId)
                        .orElseThrow(() -> new RuleException("suggestion.not.found"));


        Order  order = suggestion.getOrder();
        order.setSuggestion(suggestion);
        order.setStatus(OrderStatusEnum.COMING);
        orderJpaRepository.save(order);
    }

    public List<SuggestionResponseDto> getAllSuggestionsDto(List<Suggestion> suggestions) {
        return suggestions.stream().map(this::convertToSuggestionsResponseDto).collect(Collectors.toList());
    }

    private SuggestionResponseDto convertToSuggestionsResponseDto(Suggestion suggestion) {
        return new SuggestionResponseDto(
                suggestion.getId(),
                suggestion.getUser().getId(),
                suggestion.getOrder().getId(),
                suggestion.getSuggestionPrice(),
                suggestion.getStartDate(),
                suggestion.getStartTime(),
                suggestion.getDurationOfWork()
        );
    }

}
