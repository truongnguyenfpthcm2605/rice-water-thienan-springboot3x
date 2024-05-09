package org.website.thienan.ricewaterthienan.messages.mail;

import jakarta.mail.MessagingException;

public interface MailService {
    void send(MailModel mail) throws MessagingException;
    void send(String to, String subject, String body) throws MessagingException;
    void queue(MailModel mail);
    void queue(String to, String subject, String body);
}
