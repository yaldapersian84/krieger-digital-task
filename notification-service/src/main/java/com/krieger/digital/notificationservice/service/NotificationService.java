package com.krieger.digital.notificationservice.service;

import com.krieger.digital.notificationservice.model.Notification;
import com.krieger.digital.notificationservice.repository.NotificationRepository;
import com.krieger.digital.notificationservice.request.SendNotificationRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class NotificationService {
    private final NotificationRepository notificationRepository;

    public void save(SendNotificationRequest request) {
        var notification = Notification.builder()
                .id(UUID.randomUUID().toString())
                .objectId(request.getId())
                .message(request.getMessage())
                .build();
        notificationRepository.save(notification);
    }

}
