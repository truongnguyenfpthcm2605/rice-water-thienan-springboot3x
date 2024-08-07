package org.website.thienan.ricewaterthienan.messages.mail;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class MailerServiceImpl implements MailService {
    private final JavaMailSender sender;
    private final List<MailModel> list = new ArrayList<>();

    @Override
    @Async
    public void send(MailModel mail) throws MessagingException {
        log.info("mail sender");
        MimeMessage message = sender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "utf-8");
        helper.setFrom(mail.getFrom());
        helper.setTo(mail.getTo());
        helper.setSubject(mail.getSubject());
        helper.setText(mail.getContent(), true);
        helper.setReplyTo(mail.getFrom());
        String[] cc = mail.getCc();
        String[] bcc = mail.getBcc();
        List<File> files = mail.getFiles();
        if (cc != null && cc.length > 0) {
            helper.setCc(cc);
        }
        if (bcc != null && bcc.length > 0) {
            helper.setBcc(bcc);
        }
        if (files != null) {
            for (File file : files) {
                helper.addAttachment(file.getName(), file);
            }
        }
        sender.send(message);
    }

    @Override
    public void send(String to, String subject, String body) throws MessagingException {
        this.send(MailModel.builder()
                .to(to)
                .subject(subject)
                .content(body)
                .from("travelbee@gmail.com")
                .build());
    }

    @Override
    public void queue(MailModel mail) {
        list.add(mail);
    }

    @Override
    public void queue(String to, String subject, String body) {
        this.queue(MailModel.builder().to(to).subject(subject).content(body).build());
    }

    @Scheduled(fixedDelay = 1000, initialDelay = 1000)
    public void run() throws MessagingException {
        while (!list.isEmpty()) {
            MailModel mail = list.remove(0);
            this.send(mail);
        }
    }
}
