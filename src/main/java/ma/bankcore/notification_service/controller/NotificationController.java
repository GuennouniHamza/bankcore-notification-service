package ma.bankcore.notification_service.controller;


import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import ma.bankcore.notification_service.dto.NotificationRequest;
import ma.bankcore.notification_service.dto.NotificationResponse;
import ma.bankcore.notification_service.service.NotificationService;

@RestController
@RequestMapping("api/v1/notifications")
public class NotificationController {
	
	private final NotificationService notificationService;
	
	public NotificationController(NotificationService notificationService) {
		this.notificationService=notificationService;
	};
	
	@PostMapping("/envoyer")
	public ResponseEntity<NotificationResponse>envoyerNotification(@Valid @RequestBody NotificationRequest request){
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(notificationService.envoyerNotification(request));
	}
	
	@GetMapping("/destinataire/{email}")
	public ResponseEntity<List<NotificationResponse>> getByDestinataire(
            @PathVariable String email) {
        return ResponseEntity.ok(
            notificationService.getNotificationsByDestinataire(email));
    }	
	
	 @GetMapping("/echouees")
	    public ResponseEntity<List<NotificationResponse>> getEchouees() {
	        return ResponseEntity.ok(notificationService.getNotificationsEchouees());
	    }
}
