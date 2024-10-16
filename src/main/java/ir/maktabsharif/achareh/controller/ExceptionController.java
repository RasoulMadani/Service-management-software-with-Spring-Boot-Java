package ir.maktabsharif.achareh.controller;

import ir.maktabsharif.achareh.dto.exception.ExceptionResponse;
import ir.maktabsharif.achareh.exception.RuleException;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class ExceptionController {
    private final MessageSourceAccessor messageSourceAccessor;

    public ExceptionController(MessageSourceAccessor messageSourceAccessor) {
        this.messageSourceAccessor = messageSourceAccessor;
    }

    @ExceptionHandler(RuleException.class)
    public ResponseEntity<List<ExceptionResponse>> handleRuleException(RuleException ruleException) {
        return ResponseEntity
                .status(400)
                .body(Collections.singletonList(getBuild(ruleException)));
    }

    private ExceptionResponse getBuild(RuleException ruleException) {
        return ExceptionResponse.builder()
                .message(messageSourceAccessor.getMessage(ruleException.getMessage(), ruleException.getLocalizedMessage())) // پیام پیش‌فرض اگر کلید یافت نشود
                .property(ruleException.getMessage())
                .build();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<List<ExceptionResponse>> handlerMethodArgumentNotValidException(MethodArgumentNotValidException methodArgumentNotValidException) {
        return ResponseEntity.status(400) // تغییر وضعیت به 400
                .body(convertFieldErrorsToResponses(methodArgumentNotValidException));
    }

    private List<ExceptionResponse> convertFieldErrorsToResponses(MethodArgumentNotValidException methodArgumentNotValidException) {
        return methodArgumentNotValidException.getFieldErrors().stream().map(
                error -> ExceptionResponse
                        .builder()
                        .message(error.getDefaultMessage())
                        .property(error.getField()) // تغییر به getField
                        .build()
        ).collect(Collectors.toList());
    }

//    @ExceptionHandler(IllegalArgumentException.class)
//    public ResponseEntity<String> handleIllegalArgument(IllegalArgumentException ex) {
//        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
//    }

}

