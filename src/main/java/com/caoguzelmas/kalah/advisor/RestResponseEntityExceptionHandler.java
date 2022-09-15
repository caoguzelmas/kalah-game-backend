package com.caoguzelmas.kalah.advisor;

import com.caoguzelmas.kalah.exceptions.GameException;
import com.caoguzelmas.kalah.exceptions.IllegalMoveException;
import com.caoguzelmas.kalah.model.dto.GameResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(GameException.class)
    public ResponseEntity<GameResponseDto> handleGameException(GameException gameException) {

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(GameResponseDto
                .builder()
                .isSuccess(false)
                .message(gameException.getMessage())
                .build());
    }

    @ExceptionHandler(IllegalMoveException.class)
    public ResponseEntity<GameResponseDto> handleIllegalMoveException(IllegalMoveException illegalMoveException) {

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(GameResponseDto
                .builder()
                .isSuccess(false)
                .message(illegalMoveException.getMessage())
                .build());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<GameResponseDto> handleException(Exception exception) {

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(GameResponseDto
                .builder()
                .isSuccess(false)
                .message(exception.getMessage())
                .build());
    }

}
