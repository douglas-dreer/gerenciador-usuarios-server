package io.github.gabitxt.gerenciamentousuario.globalizacao;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;



import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.RestControllerAdvice;

// CLASSE DE VALIDAÇÃO DE ERRO
@RestControllerAdvice
public class GlobalExceptionHandler {

    public ResponseEntity<?> validateExceptionHandler(MethodArgumentNotValidException e) {

        ErrorReasponse response = new  ErrorReasponse();

        e.getAllErrors().forEach(item ->{
            String field = ((FieldError) item).getField();
            String message = item.getDefaultMessage();
        });
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }



}

