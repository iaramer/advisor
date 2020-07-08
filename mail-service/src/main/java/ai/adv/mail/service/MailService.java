package ai.adv.mail.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

@Service
@Slf4j
public class MailService {

    private static final String SMTP_HOST = "smtp.gmail.com";
    private static final String SMTP_PORT = "587";
    private static final String TEMPLATE_MESSAGE = "Hello,\n\nThere's a new message for you:\n%s";
    private static final String MESSAGE_SUBJECT = "Advisor service Info";

    @Value("${mail-service.email.address}")
    private String MAIL_SERVICE_EMAIL_ADDRESS;
    @Value("${mail-service.email.password}")
    private String MAIL_SERVICE_EMAIL_PASSWORD;
    @Value("${mail-service.test-address}")
    private String TO;

    public void sendEmailMessage(String emailMessage) {
        Properties properties = getProperties();
        Session session = getSession(properties);

        try {
            Message message = getMessage(emailMessage, session);
            Transport.send(message);
            log.info("Message sent successfully");
        } catch (MessagingException e) {
            e.printStackTrace();
            log.error("An error occurred during message sending");
        }
    }

    private Session getSession(Properties properties) {
        return Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(MAIL_SERVICE_EMAIL_ADDRESS, MAIL_SERVICE_EMAIL_PASSWORD);
            }
        });
    }

    private Properties getProperties() {
        Properties properties = new Properties();
        properties.put("mail.smtp.host", SMTP_HOST);
        properties.put("mail.smtp.port", SMTP_PORT);
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        return properties;
    }

    private Message getMessage(String emailMessage, Session session) throws MessagingException {
        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(MAIL_SERVICE_EMAIL_ADDRESS));
        message.addRecipient(Message.RecipientType.TO, new InternetAddress(TO));
        message.setSubject(MESSAGE_SUBJECT);
        message.setText(String.format(TEMPLATE_MESSAGE, emailMessage));
        return message;
    }
}
