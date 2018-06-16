package ua.social.network.notificationservice.service.impl;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailServiceClientBuilder;
import com.amazonaws.services.simpleemail.model.Body;
import com.amazonaws.services.simpleemail.model.Content;
import com.amazonaws.services.simpleemail.model.Destination;
import com.amazonaws.services.simpleemail.model.Message;
import com.amazonaws.services.simpleemail.model.SendEmailRequest;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import it.ozimov.springboot.mail.service.TemplateService;
import lombok.SneakyThrows;
import ua.social.network.notificationservice.api.EmailPayload;
import ua.social.network.notificationservice.domain.Notification;
import ua.social.network.notificationservice.properties.SESProperties;
import ua.social.network.notificationservice.service.NotificationSender;

/**
 * @author Mykola Yashchenko
 */
@Service
@Profile("production")
public class SESNotificationSender implements NotificationSender<EmailPayload> {

    private final String emailFrom;
    private final AmazonSimpleEmailService amazonSimpleEmailService;
    private final TemplateService templateService;

    public SESNotificationSender(final SESProperties configuration, final TemplateService templateService) {
        this.templateService = templateService;
        final AmazonSimpleEmailServiceClientBuilder builder = AmazonSimpleEmailServiceClientBuilder.standard();
        builder.setCredentials(new AWSStaticCredentialsProvider(new BasicAWSCredentials(configuration.getAccessKey(), configuration.getSecretKey())));
        builder.setRegion(configuration.getRegion());

        this.emailFrom = configuration.getEmailFrom();
        this.amazonSimpleEmailService = builder.build();
    }

    @Override
    @SneakyThrows
    public void send(final Notification notification) {
        final EmailPayload payload = (EmailPayload) notification.payload;
        final String body = templateService.mergeTemplateIntoString(payload.template.template(), payload.templateModel);
        final SendEmailRequest request = new SendEmailRequest(
                emailFrom,
                new Destination()
                        .withToAddresses(payload.email),
                new Message(
                        new Content(payload.subject),
                        new Body(new Content(body))
                )
        );

        amazonSimpleEmailService.sendEmail(request);
    }

    @Override
    public Class<EmailPayload> payloadType() {
        return EmailPayload.class;
    }
}
