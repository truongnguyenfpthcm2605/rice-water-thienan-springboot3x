package org.website.thienan.ricewaterthienan.exceptions;

import lombok.*;
import org.website.thienan.ricewaterthienan.enums.MessagesHanlderEnum;

import java.io.IOException;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper=false)
public class FileException extends IOException {
    private MessagesHanlderEnum messagesHanlderEnum;
}
