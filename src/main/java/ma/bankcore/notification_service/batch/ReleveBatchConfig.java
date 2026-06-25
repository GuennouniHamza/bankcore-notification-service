package ma.bankcore.notification_service.batch;

import ma.bankcore.notification_service.entity.Notification;
import ma.bankcore.notification_service.entity.StatutNotification;
import ma.bankcore.notification_service.repository.NotificationRepository;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.support.ListItemReader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;

@Configuration
@EnableScheduling
public class ReleveBatchConfig {

    private final NotificationRepository notificationRepository;
    private final JavaMailSender mailSender;
    private final JobLauncher jobLauncher;
    private final JobRepository jobRepository;//stocke l'historique des exécutions du job
    private final PlatformTransactionManager transactionManager;

   
    public ReleveBatchConfig(
            NotificationRepository notificationRepository,
            JavaMailSender mailSender,
            JobLauncher jobLauncher,
            JobRepository jobRepository,
            PlatformTransactionManager transactionManager) {
        this.notificationRepository = notificationRepository;
        this.mailSender = mailSender;
        this.jobLauncher = jobLauncher;
        this.jobRepository = jobRepository;
        this.transactionManager = transactionManager;
    }

    // ← Déclenché automatiquement le 1er de chaque mois à minuit
    @Scheduled(cron = "0 0 0 1 * *")
    public void lancerJobReleves() throws Exception {
        JobParameters params = new JobParametersBuilder()
            .addLong("time", System.currentTimeMillis())
            .toJobParameters();
        jobLauncher.run(relevesMensuelsJob(), params);
    }

    @Bean
    public Job relevesMensuelsJob() {
        return new JobBuilder("relevesMensuelsJob", jobRepository)
            .start(envoyerRelevesStep())
            .build();
    }
    

    @Bean
    public Step envoyerRelevesStep() {
        return new StepBuilder("envoyerRelevesStep", jobRepository)
            .<Notification, SimpleMailMessage>chunk(10, transactionManager)
            .reader(notificationReader())
            .processor(notificationProcessor())
            .writer(notificationWriter())
            .build();
    }

    @Bean
    public ItemReader<Notification> notificationReader() {
        return new ListItemReader<>(
            notificationRepository.findByStatut(StatutNotification.ENVOYE)
        );
    }

    @Bean
    public ItemProcessor<Notification, SimpleMailMessage> notificationProcessor() {
        return notification -> {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(notification.getDestinataire());
            message.setSubject("Relevé mensuel BankCore");
            message.setText(
                "Bonjour,\n\n" +
                "Voici votre relevé mensuel BankCore.\n\n" +
                "Dernière notification : " + notification.getSujet() + "\n" +
                "Date : " + notification.getDateEnvoi() + "\n\n" +
                "Cordialement,\nBankCore"
            );
            return message;
        };
    }

    @Bean
    public ItemWriter<SimpleMailMessage> notificationWriter() {
        return messages -> {
            for (SimpleMailMessage message : messages) {
                mailSender.send(message);
            }
        };
    }
}