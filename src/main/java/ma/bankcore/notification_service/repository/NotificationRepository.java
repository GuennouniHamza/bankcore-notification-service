package ma.bankcore.notification_service.repository;

import java.util.List;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ma.bankcore.notification_service.entity.Notification;
import ma.bankcore.notification_service.entity.StatutNotification;

@Repository
public interface NotificationRepository extends JpaRepository<Notification,Long> {

    List<Notification> findByDestinataire(String destinataire);

    List<Notification> findByStatut(StatutNotification statut);

    List<Notification> findByDestinataireAndStatut(
        String destinataire, StatutNotification statut);
}
