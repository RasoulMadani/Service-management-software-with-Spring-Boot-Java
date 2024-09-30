package ir.maktabsharif.achareh.dto.exception;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
@Builder
public class ExceptionResponse {
    private final String message;
    private final String property;
}
