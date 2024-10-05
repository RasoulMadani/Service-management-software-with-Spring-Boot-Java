package ir.maktabsharif.achareh.service.suggestionService;

import ir.maktabsharif.achareh.dto.suggesion.SuggestionRequestDto;
import ir.maktabsharif.achareh.dto.suggesion.SuggestionResponseDto;
import ir.maktabsharif.achareh.entity.*;
import ir.maktabsharif.achareh.enums.StatusOrderEnum;
import ir.maktabsharif.achareh.exception.RuleException;
import ir.maktabsharif.achareh.repository.OrderJpaRepository;
import ir.maktabsharif.achareh.repository.SuggestionJpaRepository;
import ir.maktabsharif.achareh.repository.UserJpaRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SuggestionServiceImpl implements SuggestionService {
    private final SuggestionJpaRepository suggestionJpaRepository;
    private final UserJpaRepository userJpaRepository;
    private final OrderJpaRepository orderJpaRepository;

    @Override
    @Transactional
    public SuggestionResponseDto save(SuggestionRequestDto suggestionRequestDto) {
        User findUser =
                userJpaRepository.findById(suggestionRequestDto.user_id())
                        .orElseThrow(() -> new RuleException("user.not.found"));

        Order order =
                orderJpaRepository.findById(suggestionRequestDto.order_id())
                        .orElseThrow(() -> new RuleException("order.not.found"));

        if (order.getSubDuty().getBase_price() > suggestionRequestDto.suggestionPrice())
            throw new RuleException("suggestion_price.smaller.than.base_price");

        Suggestion suggestion = Suggestion.builder()
                .suggestionPrice(suggestionRequestDto.suggestionPrice())
                .startDate(suggestionRequestDto.date())
                .startTime(suggestionRequestDto.time())
                .durationOfWork(suggestionRequestDto.durationOfWork())
                .order(order)
                .user(findUser)
                .build();
        Suggestion savedSuggestion = suggestionJpaRepository.save(suggestion);
        order.setStatus(StatusOrderEnum.SELECT);
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
}
