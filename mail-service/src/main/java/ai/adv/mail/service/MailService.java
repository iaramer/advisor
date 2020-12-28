package ai.adv.mail.service;

import java.util.Properties;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class MailService {

  private static final String SMTP_HOST = "smtp.gmail.com";
  private static final String SMTP_PORT = "587";
  private static final String TEMPLATE_MESSAGE = "Hello,\n\nThere's a new message for you:\n%s";
  private static final String MESSAGE_SUBJECT = "Advisor service Info";

  @Value("${mail-service.email.address}")
  private String mailServiceEmailAddress;
  @Value("${mail-service.email.password}")
  private String mailServiceEmailPassword;
  @Value("${mail-service.test-address}")
  private String to;

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
        return new PasswordAuthentication(mailServiceEmailAddress, mailServiceEmailPassword);
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
    message.setFrom(new InternetAddress(mailServiceEmailAddress));
    message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
    message.setSubject(MESSAGE_SUBJECT);
    message.setText(String.format(TEMPLATE_MESSAGE, emailMessage));
    return message;
  }
}
