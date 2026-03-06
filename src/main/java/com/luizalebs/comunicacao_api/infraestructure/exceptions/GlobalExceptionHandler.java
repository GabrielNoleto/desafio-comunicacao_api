package com.luizalebs.comunicacao_api.infraestructure.exceptions;

import com.luizalebs.comunicacao_api.infraestructure.exceptions.errorDTO.ErrorResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler {

@ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity <ErrorResponseDTO>handlerResourceNotFoundExcepiton(ResourceNotFoundException ex, HttpServletRequest request){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(buildError(
                    HttpStatus.NOT_FOUND.value(),
                    ex.getMessage(),
                    request.getRequestURI(),
                    "Not Found"
            ) );
}

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity <ErrorResponseDTO>handlerIllegalArgumentException(IllegalArgumentException ex, HttpServletRequest request){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(buildError(
                HttpStatus.BAD_REQUEST.value(),
                ex.getMessage(),
                request.getRequestURI(),
                "Bad Request"
        ) );
    }

    @ExceptionHandler(InfrastructureException.class)
    public ResponseEntity<ErrorResponseDTO> handlerInfrastructureException(InfrastructureException ex, HttpServletRequest request){
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(buildError(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                ex.getMessage(),
                request.getRequestURI(),
                "Internal server error"
        ) );
    }

    private ErrorResponseDTO buildError(int status, String mensagem, String path, String error) {
        return ErrorResponseDTO.builder()
                .timestamp(LocalDateTime.now())
                .status(status)
                .error(error)
                .message(mensagem)
                .path(path)
                .build();
    }

}
