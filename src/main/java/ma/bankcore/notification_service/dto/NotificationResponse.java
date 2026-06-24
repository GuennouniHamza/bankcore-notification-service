package ma.bankcore.notification_service.dto;

import java.time.LocalDateTime;

import ma.bankcore.notification_service.entity.StatutNotification;

public class NotificationResponse {
    private Long id;
    private String destinataire;
    private String sujet;
    private String message;
    private StatutNotification statut;
    private LocalDateTime dateEnvoi;
    
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getDestinataire() { return destinataire; }
    public void setDestinataire(String d) { this.destinataire = d; }
    public String getSujet() { return sujet; }
    public void setSujet(String sujet) { this.sujet = sujet; }
    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
    public StatutNotification getStatut() { return statut; }
    public void setStatut(StatutNotification statut) { this.statut = statut; }
    public LocalDateTime getDateEnvoi() { return dateEnvoi; }
    public void setDateEnvoi(LocalDateTime d) { this.dateEnvoi = d; }
}
