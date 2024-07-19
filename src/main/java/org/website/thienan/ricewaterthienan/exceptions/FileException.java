package org.website.thienan.ricewaterthienan.exceptions;

import java.io.IOException;

import org.website.thienan.ricewaterthienan.enums.MessagesHanlderEnum;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class FileException extends IOException {
    private MessagesHanlderEnum messagesHanlderEnum;
}
