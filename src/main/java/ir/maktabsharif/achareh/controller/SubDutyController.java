package ir.maktabsharif.achareh.controller;

import ir.maktabsharif.achareh.dto.duty.DutyRequestDto;
import ir.maktabsharif.achareh.dto.duty.DutyResponseDto;
import ir.maktabsharif.achareh.dto.subDuty.SubDutyRequestDto;
import ir.maktabsharif.achareh.dto.subDuty.SubDutyResponseDto;
import ir.maktabsharif.achareh.service.dutyService.DutyService;
import ir.maktabsharif.achareh.service.subDutyService.SubDutyService;
import ir.maktabsharif.achareh.utils.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/sub_duty")
public class SubDutyController {
    private final SubDutyService subDutyService;

    @PostMapping
    @PreAuthorize("hasAuthority('ADD SUB_DUTY')")
    public ResponseEntity<SubDutyResponseDto> save(@Valid @RequestBody SubDutyRequestDto subDutyRequestDto) {

        return ResponseEntity.ok(subDutyService.save(subDutyRequestDto));
    }
    @PutMapping
    @PreAuthorize("hasAuthority('EDIT SUB_DUTY')")
    public ResponseEntity<SubDutyResponseDto> update(@Valid @RequestBody SubDutyRequestDto subDutyRequestDto) {

        return ResponseEntity.ok(subDutyService.update(subDutyRequestDto));
    }

    @PostMapping("/{userId}/subDuty/{subDutyId}")
    @PreAuthorize("hasAuthority('ADD USER TO SUB_DUTY')")
    public ResponseEntity<ApiResponse> enrollUserInSubDuty(@PathVariable Long userId, @PathVariable Long subDutyId) {
        subDutyService.enrollUserInSubDuty(userId, subDutyId);
        return ResponseEntity.ok(new ApiResponse("user.enrolled.in.sub_duty.successfully",true));
    }
}