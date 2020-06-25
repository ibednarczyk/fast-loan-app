package com.loan_application.representation;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.loan_application.domain.user.User;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@Builder
public class ApplicationDto {



    private Long appId;
    private BigDecimal principal;
    private Integer term;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime submissionDate;
    private String status;
    private User user;
}
