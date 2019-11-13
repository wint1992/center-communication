package ru.ithex.center.communication.controller.advice;

import org.springframework.dao.InvalidDataAccessResourceUsageException;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.ithex.baseweb.controller.advice.BaseControllerAdvice;
import ru.ithex.baseweb.model.dto.response.ResponseWrapperDTO;
import ru.ithex.baseweb.model.dto.response.error.BadRequestError;
import ru.ithex.baseweb.model.dto.response.error.InternalServerError;
import ru.ithex.center.communication.emailsender.exception.*;
import ru.ithex.center.communication.exception.CommunicationDtoValidationException;
import ru.ithex.center.communication.exception.EmailMappingException;

@RestControllerAdvice
public class BaseCommunicationControllerAdvice extends BaseControllerAdvice {
    @ExceptionHandler(InvalidDataAccessResourceUsageException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseWrapperDTO baseHandle(InvalidDataAccessResourceUsageException e, HttpRequest request) {
        return ResponseWrapperDTO.error(new InternalServerError("Ошибка доступа к данным"));
    }

    @ExceptionHandler(AttachmentMessagingException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseWrapperDTO baseHandle(AttachmentMessagingException e, HttpRequest request) {
        return ResponseWrapperDTO.error(new InternalServerError(e.getMessage()));
    }

    @ExceptionHandler(EmailMappingException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseWrapperDTO baseHandle(EmailMappingException e, HttpRequest request) {
        return ResponseWrapperDTO.error(new InternalServerError(e.getMessage()));
    }

    @ExceptionHandler(EmailSenderException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseWrapperDTO baseHandle(EmailSenderException e, HttpRequest request) {
        return ResponseWrapperDTO.error(new InternalServerError(e.getMessage()));
    }

    @ExceptionHandler(EmailDtoValidationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseWrapperDTO baseHandle(EmailDtoValidationException e, HttpRequest request) {
        return ResponseWrapperDTO.error(new BadRequestError(e.getMessage()));
    }

    @ExceptionHandler(CommunicationDtoValidationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseWrapperDTO baseHandle(CommunicationDtoValidationException e, HttpRequest request) {
        return ResponseWrapperDTO.error(new BadRequestError(e.getMessage()));
    }

    @ExceptionHandler(FreeMarkerTemplateException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseWrapperDTO baseHandle(FreeMarkerTemplateException e, HttpRequest request) {
        return ResponseWrapperDTO.error(new BadRequestError(e.getMessage()));
    }

    @ExceptionHandler(EmailTemplateDataException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseWrapperDTO baseHandle(EmailTemplateDataException e, HttpRequest request) {
        return ResponseWrapperDTO.error(new InternalServerError(e.getMessage()));
    }
}