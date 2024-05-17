package org.website.thienan.ricewaterthienan.exceptions;

import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.website.thienan.ricewaterthienan.dto.response.MessageResponse;
import org.website.thienan.ricewaterthienan.enums.MessagesHanlderEnum;
import java.nio.file.AccessDeniedException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandling {

    @ExceptionHandler({Exception.class, Throwable.class})
    public ResponseEntity<MessageResponse> handlingExceptionGlobal(Exception ex){
        log.info("Exception Global :", ex.getMessage());
        return new ResponseEntity<>(MessageResponse.builder().
                    code(MessagesHanlderEnum.UNCATEGORIZED_EXCEPTION.getCode())
                .message(MessagesHanlderEnum.UNCATEGORIZED_EXCEPTION.getMessage() + ex.getMessage())
                .timeStamp(LocalDateTime.now())
                .build(), MessagesHanlderEnum.UNCATEGORIZED_EXCEPTION.getStatusCode()
                );
    }

    @ExceptionHandler(FileException.class)
    public ResponseEntity<MessageResponse> handlingFileException(FileException ex){
        log.info("File Exception :", ex.getMessage());
        return new ResponseEntity<>(MessageResponse.builder().
                code(MessagesHanlderEnum.FILE_EXCEPTION.getCode())
                .message(MessagesHanlderEnum.FILE_EXCEPTION.getMessage())
                .timeStamp(LocalDateTime.now())
                .build(), MessagesHanlderEnum.FILE_EXCEPTION.getStatusCode()
        );
    }

    @ExceptionHandler(MailException.class)
    public ResponseEntity<MessageResponse> handlingMailException(MailException ex){
        log.info("Mail Exception :", ex.getMessage());
        return new ResponseEntity<>(MessageResponse.builder().
                code(MessagesHanlderEnum.MAIL_EXCEPTION.getCode())
                .message(MessagesHanlderEnum.MAIL_EXCEPTION.getMessage())
                .timeStamp(LocalDateTime.now())
                .build(), MessagesHanlderEnum.MAIL_EXCEPTION.getStatusCode()
        );
    }

    @ExceptionHandler(ResourceExistingException.class)
    public ResponseEntity<MessageResponse> handlingResourceExitException(ResourceExistingException ex){
        log.info("Resource Exit Exception :", ex.getMessage());
        return new ResponseEntity<>(MessageResponse.builder().
                code(MessagesHanlderEnum.RESOURCE_EXIT.getCode())
                .message(MessagesHanlderEnum.RESOURCE_EXIT.getMessage())
                .timeStamp(LocalDateTime.now())
                .build(), MessagesHanlderEnum.RESOURCE_EXIT.getStatusCode()

        );
    }
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<MessageResponse> handlingResourceNotFoundException(ResourceNotFoundException ex){
        log.info("Resource Notfound Exception :", ex.getMessage());
        return new ResponseEntity<>(MessageResponse.builder().
                code(MessagesHanlderEnum.NOTFOUND.getCode())
                .message(MessagesHanlderEnum.NOTFOUND.getMessage() + ex.getMessage())
                .timeStamp(LocalDateTime.now())
                .build(), MessagesHanlderEnum.NOTFOUND.getStatusCode()
        );
    }

    @ExceptionHandler({PermissionDeniedException.class, AccessDeniedException.class})
    public ResponseEntity<MessageResponse> handlingPermissionException(FileException ex){
        log.info("Permission Exception :", ex.getMessage());
        return new ResponseEntity<>(MessageResponse.builder().
                code(MessagesHanlderEnum.PERMISSION.getCode())
                .message(MessagesHanlderEnum.PERMISSION.getMessage())
                .timeStamp(LocalDateTime.now())
                .build(), MessagesHanlderEnum.PERMISSION.getStatusCode()
        );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<MessageResponse> methodHandleExceptionValidation(MethodArgumentNotValidException e){
        log.info("Validation Exception :", e.getMessage());
       List<String> fieldsErrors = e.getBindingResult().getFieldErrors().stream().map(fieldError -> fieldError.getDefaultMessage()).collect(Collectors.toList());
        return new ResponseEntity<>(MessageResponse.builder()
                .code(500)
                .message("Error Validation")
                .timeStamp(LocalDateTime.now())
                .data(fieldsErrors).build(), HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(DataAccessException.class)
    public ResponseEntity<MessageResponse> handlingSQLQueryException(DataAccessException ex){
        log.info("DataAccessException Exception :", ex.getMessage());
        return new ResponseEntity<>(MessageResponse.builder().
                code(MessagesHanlderEnum.SQL_EXCEPTION.getCode())
                .message(MessagesHanlderEnum.SQL_EXCEPTION.getMessage())
                .timeStamp(LocalDateTime.now())
                .build(), MessagesHanlderEnum.SQL_EXCEPTION.getStatusCode()
        );
    }




}
