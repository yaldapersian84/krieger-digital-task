package com.krieger.digital.notificationservice.request;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SendNotificationRequest {
    private String id;
    private String message;
}
