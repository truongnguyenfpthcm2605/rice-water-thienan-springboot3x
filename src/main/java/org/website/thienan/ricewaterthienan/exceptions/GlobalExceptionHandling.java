package org.website.thienan.ricewaterthienan.exceptions;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.ui.Model;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.io.IOException;
import java.nio.file.AccessDeniedException;
import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandling {

    @ExceptionHandler({Exception.class, Throwable.class})
    public String handlingExceptionGlobal(Exception ex){
        log.info("Exception Global {} :", ex.getMessage());
        return "errors/404";
    }

    @ExceptionHandler({FileException.class, IOException.class})
    public String handlingFileException(FileException ex){
        log.info("File Exception {}:", ex.getMessage());
        return "errors/404";
    }

    @ExceptionHandler(MailException.class)
    public String handlingMailException(MailException ex){
        log.info("Mail Exception {}:", ex.getMessage());
        return "errors/404";
    }

    @ExceptionHandler(ResourceExistingException.class)
    public String handlingResourceExitException(ResourceExistingException ex){
        log.info("Resource Exit Exception {}:", ex.getMessage());
        return "errors/404";
    }
    @ExceptionHandler(ResourceNotFoundException.class)
    public String handlingResourceNotFoundException(ResourceNotFoundException ex){
        log.info("Resource Notfound Exception {}:", ex.getMessage());
        return "errors/404";
    }

    @ExceptionHandler({PermissionDeniedException.class, AccessDeniedException.class})
    public String handlingPermissionException(FileException ex){
        log.info("Permission Exception {}:", ex.getMessage());
        return "errors/404";
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public String methodHandleExceptionValidation(MethodArgumentNotValidException e, Model model){
        log.info("Validation Exception {}:", e.getMessage());
        List<String> fieldsErrors = e.getBindingResult().getFieldErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).collect(Collectors.toList());
        model.addAttribute("fieldsErrors", fieldsErrors);
        return "errors/500";
    }


}
