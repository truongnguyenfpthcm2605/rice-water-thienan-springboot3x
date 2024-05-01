package org.website.thienan.ricewaterthienan.exceptions;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.website.thienan.ricewaterthienan.enums.MessagesHanlderEnum;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResourceNotFoundException extends  RuntimeException{
    private MessagesHanlderEnum messagesHanlderEnum;
}
