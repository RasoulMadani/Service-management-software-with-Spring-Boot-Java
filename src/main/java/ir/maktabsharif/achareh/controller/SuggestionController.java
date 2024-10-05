package ir.maktabsharif.achareh.controller;

import ir.maktabsharif.achareh.dto.subDuty.SubDutyRequestDto;
import ir.maktabsharif.achareh.dto.subDuty.SubDutyResponseDto;
import ir.maktabsharif.achareh.dto.suggesion.SuggestionRequestDto;
import ir.maktabsharif.achareh.dto.suggesion.SuggestionResponseDto;
import ir.maktabsharif.achareh.service.suggestionService.SuggestionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/suggestion")
@RequiredArgsConstructor
public class SuggestionController {
    private final SuggestionService suggestionService;
    @PostMapping
    public ResponseEntity<SuggestionResponseDto> save(@Valid @RequestBody SuggestionRequestDto suggestionRequestDto) {

        return ResponseEntity.ok(suggestionService.save(suggestionRequestDto));
    }
}
