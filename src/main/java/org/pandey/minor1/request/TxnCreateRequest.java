package org.pandey.minor1.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TxnCreateRequest {

    @NotBlank(message = "StudentContact must not be blank")
    private String studentContact;

    @NotBlank(message = "Book no must not be blank")
    private String bookNo;

    @Positive(message = "Amount must not be blank")
    private Integer amount;
}
