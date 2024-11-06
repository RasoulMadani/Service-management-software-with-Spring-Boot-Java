package ir.maktabsharif.achareh.controller;

import ir.maktabsharif.achareh.dto.duty.DutyFindSubDutyRequestDto;
import ir.maktabsharif.achareh.dto.duty.DutyRequestDto;
import ir.maktabsharif.achareh.dto.duty.DutyResponseDto;
import ir.maktabsharif.achareh.dto.subDuty.SubDutyResponseDto;
import ir.maktabsharif.achareh.dto.user.UserRequestDto;
import ir.maktabsharif.achareh.entity.User;
import ir.maktabsharif.achareh.service.dutyService.DutyService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/duty")
public class DutyController {
    private final DutyService dutyService;

    @PostMapping
    @PreAuthorize("hasAuthority('ADD DUTY')")
    public ResponseEntity<DutyResponseDto> save(@Valid @RequestBody DutyRequestDto dutyRequestDto) {

        return ResponseEntity.ok(dutyService.save(dutyRequestDto));
    }
    @GetMapping("/sub_duties")
    @PreAuthorize("hasAuthority('GET SUB_DUTIES')")
    public ResponseEntity<List<SubDutyResponseDto>> getSubDuties(@RequestParam Long id) {

        return ResponseEntity.ok(dutyService.getSubDuties(id));
    }
    @GetMapping
    @PreAuthorize("hasAuthority('GET DUTIES')")
    public ResponseEntity<List<DutyResponseDto>> getDuties() {

        return ResponseEntity.ok(dutyService.getDuties());
    }

}
