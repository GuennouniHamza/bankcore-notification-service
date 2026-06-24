package ma.bankcore.notification_service.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;

@Entity
@Table(name="tbl_notification")
public class Notification {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable=false)
	private String destinataire;//email de client
	
	@Column(nullable=false, length = 1000)
	private String sujet;
	
	@Column(nullable = false, length = 2000)
	private String message;

	@Enumerated(EnumType.STRING)
	private StatutNotification statut;
	
	@Column(updatable = false)
    private LocalDateTime dateEnvoi;
	
	//code exécuté automatiquement avant chaque INSERT:
	@PrePersist
	public void prePersist() {
		this.dateEnvoi=LocalDateTime.now();
		this.statut=StatutNotification.EN_ATTENTE;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

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

	public StatutNotification getStatut() {
		return statut;
	}

	public void setStatut(StatutNotification statut) {
		this.statut = statut;
	}

	public LocalDateTime getDateEnvoi() {
		return dateEnvoi;
	}

	public void setDateEnvoi(LocalDateTime dateEnvoi) {
		this.dateEnvoi = dateEnvoi;
	}
	
	
	
	
}
