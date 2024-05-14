package org.website.thienan.ricewaterthienan.exceptions;

import jakarta.mail.MessagingException;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


public class MailException extends MessagingException {
    public MailException(String s) {
        super(s);
    }
}
