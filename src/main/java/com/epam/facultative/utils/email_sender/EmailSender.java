package com.epam.facultative.utils.email_sender;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.File;
import java.io.IOException;
import java.util.Properties;

public class EmailSender {
    private static final Logger logger = LoggerFactory.getLogger(EmailSender.class);
    private final String user;
    private final Session session;

    public EmailSender(Properties properties) {
        user = properties.getProperty("mail.user");
        session = getSession(properties, user);
    }

    public void send(String email, String subject, String msg) {
        Message message = new MimeMessage(session);
        try {
            message.setFrom(new InternetAddress(user));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email));
            message.setSubject(subject);

            MimeBodyPart mimeBodyPart = new MimeBodyPart();
            mimeBodyPart.setContent(msg, "text/html; charset=utf-8");

            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(mimeBodyPart);

            message.setContent(multipart);

            Transport.send(message);
        } catch (MessagingException e) {
            logger.error(e.getMessage());
        }
    }

    public void sendCertificate(String email, String subject, String msg, String pathFile) {
        Message message = new MimeMessage(session);
        try {
            message.setFrom(new InternetAddress(user));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email));
            message.setSubject(subject);

            MimeBodyPart mimeBodyPart = new MimeBodyPart();
            mimeBodyPart.setContent(msg, "text/html; charset=utf-8");
            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(mimeBodyPart);

            MimeBodyPart attachment = new MimeBodyPart();
            File file = new File(pathFile);
            attachment.attachFile(file);
            multipart.addBodyPart(attachment);

            message.setContent(multipart);
            Transport.send(message);
        } catch (MessagingException | IOException e) {
            logger.error(e.getMessage());
        }
    }

    private static Session getSession(Properties properties, String user) {
        return Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(user, properties.getProperty("mail.password"));
            }
        });
    }
}