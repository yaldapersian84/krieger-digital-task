package com.krieger.digital.notificationservice.repository;

import com.krieger.digital.notificationservice.model.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, String> {
}
