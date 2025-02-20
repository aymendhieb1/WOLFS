package com.wolfs.models;

import java.util.Properties;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.*;

public class EmailService {
    public static void sendEmail(String recipient, String subject, String code) {
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
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipient));

            message.setSubject(MimeUtility.encodeText(subject, "UTF-8", "B"));
            MimeBodyPart htmlPart = new MimeBodyPart();

            String content = "<html>" +
                    "<head>" +
                    "<style>" +
                    "body { font-family: Arial, sans-serif; background-color: #f4f4f4; text-align: center; padding: 40px; }" +
                    ".container { background: white; padding: 30px; border-radius: 10px; " +
                    "box-shadow: 0px 4px 15px rgba(0, 0, 0, 0.1); max-width: 500px; margin: auto; }" +
                    ".logo { width: 150px; margin-bottom: 20px; display: block; margin: 0 auto; }" +
                    "h2 { color: #132A3E; margin-bottom: 15px; }" +
                    "p { font-size: 16px; color: #333; line-height: 1.6; margin-bottom: 15px; }" +
                    ".code { font-size: 24px; font-weight: bold; color: #E78D1E; background: #FFF3E0; " +
                    "padding: 10px 20px; display: inline-block; border-radius: 5px; margin: 15px 0; }" +
                    ".footer { margin-top: 25px; font-size: 12px; color: #888; }" +
                    "</style>" +
                    "</head>" +
                    "<body>" +
                    "<div class='container'>" +
                    "<img class='logo' src='cid:logo' alt='Logo'>" +
                    "<h2>Réinitialisation de votre mot de passe</h2>" +
                    "<p>Bonjour,</p>" +
                    "<p>Vous avez demandé à réinitialiser votre mot de passe. Utilisez le code suivant :</p>" +
                    "<p class='code'>" + code + "</p>"+
                    "<p>Ce code est valide pour une durée limitée de 5 minutes.</p>" +
                    "<p>Si vous n'avez pas fait cette demande, ignorez cet email.</p>" +
                    "<div class='footer'>© 2025 Votre Organisation. Tous droits réservés.</div>" +
                    "</div>" +
                    "</body>" +
                    "</html>";



            htmlPart.setContent(content, "text/html; charset=UTF-8");


            MimeBodyPart imagePart = new MimeBodyPart();
            DataSource fds = new FileDataSource("C:/Users/Dhib/IdeaProjects/Projet_Pidev/src/main/resources/images/primary.png");
            imagePart.setDataHandler(new DataHandler(fds));
            imagePart.setHeader("Content-ID", "<logo>");
            imagePart.setDisposition(MimeBodyPart.INLINE);

            // 📌 Partie 3 : Regrouper les parties dans un Multipart
            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(htmlPart);
            multipart.addBodyPart(imagePart);

            message.setContent(multipart);

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

