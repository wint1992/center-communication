package ru.ithex.center.communication.controller.advice;

import org.springframework.dao.InvalidDataAccessResourceUsageException;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import ru.ithex.baseweb.model.dto.response.ResponseWrapperDTO;
import ru.ithex.baseweb.model.dto.response.error.InternalServerError;

public class BaseCommunicationControllerAdvcie {
    @ExceptionHandler(InvalidDataAccessResourceUsageException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseWrapperDTO baseHandle(Exception e, HttpRequest request) {
        return ResponseWrapperDTO.error(new InternalServerError(e.getMessage()));
    }
}
