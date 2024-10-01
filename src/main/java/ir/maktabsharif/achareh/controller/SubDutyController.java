package ir.maktabsharif.achareh.controller;

import ir.maktabsharif.achareh.dto.duty.DutyRequestDto;
import ir.maktabsharif.achareh.dto.duty.DutyResponseDto;
import ir.maktabsharif.achareh.dto.subDuty.SubDutyRequestDto;
import ir.maktabsharif.achareh.dto.subDuty.SubDutyResponseDto;
import ir.maktabsharif.achareh.service.dutyService.DutyService;
import ir.maktabsharif.achareh.service.subDutyService.SubDutyService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/sub_duty")
public class SubDutyController {
    private final SubDutyService subDutyService;

    @PostMapping
    public ResponseEntity<SubDutyResponseDto> save(@Valid @RequestBody SubDutyRequestDto subDutyRequestDto) {

        return ResponseEntity.ok(subDutyService.save(subDutyRequestDto));
    }
    @PutMapping
    public ResponseEntity<SubDutyResponseDto> update(@Valid @RequestBody SubDutyRequestDto subDutyRequestDto) {

        return ResponseEntity.ok(subDutyService.update(subDutyRequestDto));
    }
}