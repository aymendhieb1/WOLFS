package com.wolfs.models;

import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.*;

public class EmailService {
    public static void sendEmail(String recipient, String subject, String content) {
        final String username = "youssef.dhib@esprit.tn";
        final String password = "sgqf mada uavi abfm";

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        javax.mail.Session session = javax.mail.Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try {
            // Création du message
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipient));

            // Sujet encodé en UTF-8
            message.setSubject(MimeUtility.encodeText(subject, "UTF-8", "B"));

            // Utilisation de HTML pour le contenu
            message.setContent(content, "text/html; charset=UTF-8");

            // Affichage du contenu pour débogage
            System.out.println("Contenu de l'email : " + content);

            // Envoi de l'email
            Transport.send(message);
            System.out.println("✅ Email envoyé avec succès !");
        } catch (Exception e) {
            // Gestion des erreurs
            e.printStackTrace();
            System.out.println("❌ Une erreur est survenue lors de l'envoi de l'email.");
        }
    }
}
