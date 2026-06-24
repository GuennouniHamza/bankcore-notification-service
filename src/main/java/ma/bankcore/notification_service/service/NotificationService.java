package ma.bankcore.notification_service.service;

import java.util.List;

import ma.bankcore.notification_service.dto.NotificationRequest;
import ma.bankcore.notification_service.dto.NotificationResponse;

public interface NotificationService {
	
	NotificationResponse envoyerNotification(NotificationRequest request);
	
	List<NotificationResponse> getNotificationsByDestinataire(String Destinataire);

    List<NotificationResponse> getNotificationsEchouees();
}
