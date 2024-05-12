package org.website.thienan.ricewaterthienan.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;
import org.website.thienan.ricewaterthienan.messages.MessageValidation;


@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BranchRequest extends BaseRequest {
    @NotBlank(message = MessageValidation.NAME_BRANCH_MESSAGE)
    String name;
    @NotBlank(message = MessageValidation.LINK_BRANCH_MESSAGE)
    String link;
    Long views;
    String accountId;
}
