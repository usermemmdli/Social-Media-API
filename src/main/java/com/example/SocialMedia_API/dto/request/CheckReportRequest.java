package com.example.SocialMedia_API.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CheckReportRequest {
    private String id;
    @NotNull(message = "Status cannot be blank")
    private Boolean status;
}
