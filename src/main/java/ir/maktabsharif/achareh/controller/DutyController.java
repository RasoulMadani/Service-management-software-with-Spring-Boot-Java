package ir.maktabsharif.achareh.controller;

import ir.maktabsharif.achareh.dto.duty.DutyRequestDto;
import ir.maktabsharif.achareh.dto.duty.DutyResponseDto;
import ir.maktabsharif.achareh.dto.user.UserRequestDto;
import ir.maktabsharif.achareh.entity.User;
import ir.maktabsharif.achareh.service.dutyService.DutyService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/duty")
public class DutyController {
    private final DutyService dutyService;

    @PostMapping
    public ResponseEntity<DutyResponseDto> save(@Valid @RequestBody DutyRequestDto dutyRequestDto) {

        return ResponseEntity.ok(dutyService.save(dutyRequestDto));
    }

}
