package com.bajaj.dto;

import lombok.Data;

@Data
public class WebhookRequest {
    private String name;
    private String regNo;
    private String email;
}
