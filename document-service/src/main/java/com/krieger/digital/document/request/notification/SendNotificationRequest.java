package com.krieger.digital.document.request.notification;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SendNotificationRequest {
    private String id;
    private String message;
}
