package org.website.thienan.ricewaterthienan.messages.mail;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MailModel {
     String from = "travelbee@fpt.edu.vn";
     String to;
     String subject;
     String content;
     String[] cc ;
     String[] bcc;
     List<File> files = new ArrayList<>();
}