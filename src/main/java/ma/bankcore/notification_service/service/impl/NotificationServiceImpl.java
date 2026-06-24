package ma.bankcore.notification_service.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ma.bankcore.notification_service.dto.NotificationRequest;
import ma.bankcore.notification_service.dto.NotificationResponse;
import ma.bankcore.notification_service.entity.Notification;
import ma.bankcore.notification_service.entity.StatutNotification;
import ma.bankcore.notification_service.exception.NotificationException;
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
			try {
				SimpleMailMessage email =new SimpleMailMessage();
				email.setTo(request.getDestinataire());
				email.setSubject(request.getSujet());
		        email.setText(request.getMessage());
		        mailSender.send(email);
		        saved.setStatut(StatutNotification.ENVOYE);
			}catch(MailException e) {
				saved.setStatut(StatutNotification.ECHEC);
				throw new NotificationException(
						 "Erreur envoi email vers " + request.getDestinataire(), e);
			}
		return notificationMapper.toResponse(saved);
	}

	@Override
	@Transactional(readOnly = true)
	public List<NotificationResponse> getNotificationsByDestinataire(String Destinataire) {
		return notificationRepository.findByDestinataire(Destinataire)
			.stream()
			.map(notificationMapper::toResponse)
			.collect(Collectors.toList());
	}

	@Override
	public List<NotificationResponse> getNotificationsEchouees() {
		return notificationRepository.findByStatut(StatutNotification.ECHEC)
				.stream()
				.map(notificationMapper::toResponse)
				.collect(Collectors.toList());
	}
}
