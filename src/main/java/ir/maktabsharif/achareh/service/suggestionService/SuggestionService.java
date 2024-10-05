package ir.maktabsharif.achareh.service.suggestionService;

import ir.maktabsharif.achareh.dto.suggesion.SuggestionRequestDto;
import ir.maktabsharif.achareh.dto.suggesion.SuggestionResponseDto;

import java.util.List;

public interface SuggestionService {
    SuggestionResponseDto save(SuggestionRequestDto suggestionRequestDto);
    List<SuggestionResponseDto> getAllByOrderId(Long orderId);
    void acceptSuggestionWithId(Long suggestionId);
}
