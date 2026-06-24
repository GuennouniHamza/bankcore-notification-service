package ma.bankcore.notification_service.service.impl;

import java.util.List;


import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ma.bankcore.notification_service.dto.NotificationRequest;
import ma.bankcore.notification_service.dto.NotificationResponse;
import ma.bankcore.notification_service.entity.Notification;
import ma.bankcore.notification_service.mapper.NotificationMapper;
import ma.bankcore.notification_service.repository.NotificationRepository;
import ma.bankcore.notification_service.service.NotificationService;

@Service
public class NotificationServiceImpl implements NotificationService {
	
	private final NotificationRepository notificationRepository;
    private final NotificationMapper notificationMapper;
    private final JavaMailSender mailSender;
    
    public NotificationServiceImpl(NotificationRepository notificationRepository,
            NotificationMapper notificationMapper,
            JavaMailSender mailSender) {
    	this.notificationRepository = notificationRepository;
        this.notificationMapper = notificationMapper;
        this.mailSender = mailSender;
    }

	@Override
	@Transactional
	public NotificationResponse envoyerNotification(NotificationRequest request) {
		
			Notification notification = notificationMapper.toEntity(request);
			Notification saved = notificationRepository.save(notification);
			
		return null;
	}

	@Override
	public List<NotificationResponse> getNotificationsByDestinataire(String Destinataire) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<NotificationResponse> getNotificationsEchouees() {
		// TODO Auto-generated method stub
		return null;
	}
}
