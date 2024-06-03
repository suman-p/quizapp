package com.vedik.quizapp.exception;

import com.vedik.quizapp.dto.ErrorResponseDto;
import com.vedik.quizapp.dto.GenericBuilder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                  HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        Map<String, String> validationErrors = new HashMap<>();
        List<ObjectError> validationErrorList = ex.getBindingResult().getAllErrors();
        validationErrorList.forEach(error -> {
            String fieldName = ((FieldError)error).getField();
            String errorMessage = error.getDefaultMessage();
            validationErrors.put(fieldName, errorMessage);
        });
        return ResponseEntity.badRequest().body(validationErrors);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponseDto> handleResourceNotFoundException(
            ResourceNotFoundException resourceNotFoundException, WebRequest webRequest){
        Supplier<ErrorResponseDto> supplier = ()-> new ErrorResponseDto();
        ErrorResponseDto errorResponseDto = GenericBuilder
                .of(supplier)
                .with(ErrorResponseDto::setApiPath, webRequest.getDescription(false))
                .with(ErrorResponseDto::setErrorCode, HttpStatus.INTERNAL_SERVER_ERROR)
                .with(ErrorResponseDto::setErrorMessage, resourceNotFoundException.getMessage())
                .with(ErrorResponseDto::setErrorTime, LocalDateTime.now())
                .build();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponseDto);
    }

    @ExceptionHandler(TopicAlreadyExistsException.class)
    public ResponseEntity<ErrorResponseDto> handleTopicAlreadyExistsException(
            TopicAlreadyExistsException topicAlreadyExistsException, WebRequest webRequest){
        Supplier<ErrorResponseDto> supplier = ()-> new ErrorResponseDto();
        ErrorResponseDto errorResponseDto = GenericBuilder
                .of(supplier)
                .with(ErrorResponseDto::setApiPath, webRequest.getDescription(false))
                .with(ErrorResponseDto::setErrorCode, HttpStatus.BAD_REQUEST)
                .with(ErrorResponseDto::setErrorMessage, topicAlreadyExistsException.getMessage())
                .with(ErrorResponseDto::setErrorTime, LocalDateTime.now())
                .build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponseDto);
    }
}
