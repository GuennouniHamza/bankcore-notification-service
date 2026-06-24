package ma.bankcore.notification_service.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class NotificationRequest {
	
@Email
@NotBlank(message="Email Obligatoire")
private String destinataire;

@NotBlank(message = "Sujet obligatoire")
private String sujet;

@NotBlank(message = "Message obligatoire")
private String message;

public String getDestinataire() {
	return destinataire;
}

public void setDestinataire(String destinataire) {
	this.destinataire = destinataire;
}

public String getSujet() {
	return sujet;
}

public void setSujet(String sujet) {
	this.sujet = sujet;
}

public String getMessage() {
	return message;
}

public void setMessage(String message) {
	this.message = message;
}
}
