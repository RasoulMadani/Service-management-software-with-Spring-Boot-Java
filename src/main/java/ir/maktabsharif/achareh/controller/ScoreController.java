package ir.maktabsharif.achareh.controller;

import ir.maktabsharif.achareh.dto.duty.DutyRequestDto;
import ir.maktabsharif.achareh.dto.duty.DutyResponseDto;
import ir.maktabsharif.achareh.dto.subDuty.SubDutyResponseDto;
import ir.maktabsharif.achareh.service.dutyService.DutyService;
import ir.maktabsharif.achareh.service.scoreService.ScoreService;
import ir.maktabsharif.achareh.utils.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/score")
public class ScoreController {
    private final ScoreService scoreService;

    @GetMapping
    @PreAuthorize("hasAuthority('GET SCORE')")
    public ResponseEntity<ApiResponse> getScoreUser() {

        return ResponseEntity.ok(new ApiResponse(String.valueOf(scoreService.getScore()),true));
    }

}
