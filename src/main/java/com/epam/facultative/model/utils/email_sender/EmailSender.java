package com.epam.facultative.model.utils.email_sender;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.mail.*;
import javax.mail.internet.*;
import java.io.File;
import java.io.IOException;
import java.util.Properties;

/**
 * Send emails to Users
 *
 * @author Oleksandr Panchenko
 * @version 1.0
 */
public class EmailSender {
    private static final Logger logger = LoggerFactory.getLogger(EmailSender.class);
    private final String user;
    private final Session session;

    /**
     * @param properties - should contain all required fields to properly configure
     */
    public EmailSender(Properties properties) {
        user = properties.getProperty("mail.user");
        session = getSession(properties, user);
    }

    /**
     * Sends email to one User. Email sends in html/text format with some tags
     *
     * @param subject - email's greetings
     * @param msg     - email's letter
     * @param email   - email's recipient
     */
    public void send(String email, String subject, String msg) {
        Message message = new MimeMessage(session);
        try {
            Multipart multipart = new MimeMultipart();
            setParametersFromEmail(message, multipart, email, subject, msg);
            message.setContent(multipart);
            Transport.send(message);
        } catch (MessagingException e) {
            logger.error(e.getMessage());
        }
    }

    public void sendCertificate(String email, String subject, String msg, String pathFile) {
        Message message = new MimeMessage(session);
        try {
            Multipart multipart = new MimeMultipart();
            setParametersFromEmail(message, multipart, email, subject, msg);
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

    private void setParametersFromEmail(Message message, Multipart multipart, String email, String subject, String msg) throws MessagingException {
        message.setFrom(new InternetAddress(user));
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email));
        message.setSubject(subject);
        MimeBodyPart mimeBodyPart = new MimeBodyPart();
        mimeBodyPart.setContent(msg, "text/html; charset=utf-8");
        multipart.addBodyPart(mimeBodyPart);
    }

    private Session getSession(Properties properties, String user) {
        return Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(user, properties.getProperty("mail.password"));
            }
        });
    }
}