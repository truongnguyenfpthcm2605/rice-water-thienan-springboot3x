package org.website.thienan.ricewaterthienan.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.website.thienan.ricewaterthienan.enums.MessagesHanlderEnum;

import java.io.IOException;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FileException extends IOException {
    private MessagesHanlderEnum messagesHanlderEnum;
}
