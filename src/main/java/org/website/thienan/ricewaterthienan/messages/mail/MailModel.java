package org.website.thienan.ricewaterthienan.messages.mail;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MailModel {
    String from = "gaonuocthienan@gmail.com";
    String to;
    String subject;
    String content;
    String[] cc;
    String[] bcc;
    List<File> files = new ArrayList<>();
}
