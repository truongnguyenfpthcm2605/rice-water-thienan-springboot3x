package org.website.thienan.ricewaterthienan.exception;

import lombok.*;
import org.website.thienan.ricewaterthienan.enums.MessagesHanlderEnum;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PermissionDeniedException extends  RuntimeException{
   private MessagesHanlderEnum messagesHanlderEnum;

}
