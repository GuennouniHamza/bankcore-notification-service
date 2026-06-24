package ma.bankcore.notification_service.mapper;

import ma.bankcore.notification_service.dto.NotificationRequest;
import ma.bankcore.notification_service.dto.NotificationResponse;
import ma.bankcore.notification_service.entity.Notification;
import org.springframework.stereotype.Component;

@Component
public class NotificationMapper {

    public Notification toEntity(NotificationRequest request) {
        Notification notification = new Notification();
        notification.setDestinataire(request.getDestinataire());
        notification.setSujet(request.getSujet());
        notification.setMessage(request.getMessage());
        return notification;
    }

    public NotificationResponse toResponse(Notification notification) {
        NotificationResponse response = new NotificationResponse();
        response.setId(notification.getId());
        response.setDestinataire(notification.getDestinataire());
        response.setSujet(notification.getSujet());
        response.setMessage(notification.getMessage());
        response.setStatut(notification.getStatut());
        response.setDateEnvoi(notification.getDateEnvoi());
        return response;
    }
}