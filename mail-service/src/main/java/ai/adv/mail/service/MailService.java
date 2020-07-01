package ai.adv.mail.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

@Service
@RequiredArgsConstructor
public class MailService {

    private final static String HOST = "localhost";

    @Value("#{environment.MAIL_SERVICE_TO}")
    private String TO;
    @Value("#{environment.MAIL_SERVICE_EMAIL_ADDRESS}")
    private String FROM;

    @PostConstruct
//    public void sendEmailMessage(String emailMessage) {
    public void sendEmailMessage() {

        //Get the session object
        Properties properties = System.getProperties();
        properties.setProperty("mail.smtp.host", HOST);

        Session session = Session.getDefaultInstance(properties);

        //compose the message
        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(FROM));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(TO));
            message.setSubject("Advisor service message");
            message.setText("Hello, this is example of sending email");

            // Send message
            Transport.send(message);
            System.out.println("message sent successfully...");

        } catch (MessagingException mex) {
            mex.printStackTrace();
        }
    }
}
