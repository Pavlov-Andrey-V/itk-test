package ru.apavlov.walletapp.controller.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.apavlov.walletapp.controller.dto.ErrorResponseDTO;
import ru.apavlov.walletapp.service.exception.NotEnoughBalanceException;
import ru.apavlov.walletapp.service.exception.WalletNotFoundException;

@RestControllerAdvice
public class WalletControllerAdvice {
    @ExceptionHandler(NotEnoughBalanceException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponseDTO handleNotEnoughBalanceException(NotEnoughBalanceException ex) {
        return new ErrorResponseDTO(ex.getMessage(), ex.getClass().getSimpleName());
    }

    @ExceptionHandler(WalletNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponseDTO handleWalletNotFoundException(WalletNotFoundException ex) {
        return new ErrorResponseDTO(ex.getMessage(), ex.getClass().getSimpleName());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponseDTO handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        return new ErrorResponseDTO(ex.getMessage(), ex.getClass().getSimpleName());
    }
}
