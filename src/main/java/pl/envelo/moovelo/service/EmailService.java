package pl.envelo.moovelo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender emailSender;

    public void sendSimpleMessage(String to, String subject, String text) throws MessagingException {
        MimeMessage mimeMessage = emailSender.createMimeMessage();
        mimeMessage.setFrom("Mootech@Cowmail.pl");
        mimeMessage.setRecipients(Message.RecipientType.TO, to);
        mimeMessage.setSubject(subject);
        mimeMessage.setContent(text, "text/html; charset=utf-8");
        emailSender.send(mimeMessage);
    }
}
