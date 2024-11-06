package ir.maktabsharif.achareh.controller;

import io.swagger.v3.oas.annotations.Operation;
import ir.maktabsharif.achareh.dto.suggesion.SuggestionRequestDto;
import ir.maktabsharif.achareh.dto.suggesion.SuggestionResponseDto;
import ir.maktabsharif.achareh.service.suggestionService.SuggestionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/suggestion")
@RequiredArgsConstructor
public class SuggestionController {
    private final SuggestionService suggestionService;

    @PostMapping
    @Operation(summary = "save suggestion")
    @PreAuthorize("hasAuthority('ADD SUGGESTION')")
    public ResponseEntity<SuggestionResponseDto> save(@Valid @RequestBody SuggestionRequestDto suggestionRequestDto) {

        return ResponseEntity.ok(suggestionService.save(suggestionRequestDto));
    }

    @GetMapping("/{orderId}")
    @Operation(summary = "get suggestions by order id")
    @PreAuthorize("hasAuthority('GET SUGGESTION')")
    public ResponseEntity<List<SuggestionResponseDto>> getAllByOrderId(@PathVariable Long orderId) {
        return ResponseEntity.ok(suggestionService.getAllByOrderId(orderId));
    }

    @PostMapping("/{suggestionId}")
    @PreAuthorize("hasAuthority('ACCEPT SUGGESTION')")
    @Operation(summary = "Accept a suggestion", description = "Accepts a suggestion by its ID")
    public ResponseEntity<String> acceptSuggestionWithId(@PathVariable Long suggestionId) {
        suggestionService.acceptSuggestionWithId(suggestionId);
        return ResponseEntity.ok("suggestion.accepted");
    }
}
