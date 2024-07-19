package org.website.thienan.ricewaterthienan.exceptions;

import jakarta.mail.MessagingException;

public class MailException extends MessagingException {
    public MailException(String s) {
        super(s);
    }
}
