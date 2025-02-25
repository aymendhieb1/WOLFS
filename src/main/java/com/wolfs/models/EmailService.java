package com.wolfs.models;

import java.util.Properties;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.*;

public class EmailService {
    private static final String USERNAME = "triptogo2025@gmail.com";
    private static final String PASSWORD = "rove ikbd nqpl xlnv";
    private static final String LOGO_PATH = "C:/Users/Dhib/IdeaProjects/Projet_Pidev/src/main/resources/images/primary.png";

    public static void sendEmail(String recipient, String subject, String messageContent) {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.ssl.trust", "smtp.gmail.com");
        props.put("mail.smtp.connectiontimeout", "5000");
        props.put("mail.smtp.timeout", "5000");
        props.put("mail.smtp.ssl.protocols", "TLSv1.2");


        javax.mail.Session session = javax.mail.Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(USERNAME, PASSWORD);
            }
        });


        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(USERNAME));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipient));
            message.setSubject(MimeUtility.encodeText(subject, "UTF-8", "B"));

            // 📌 Partie 1 : Contenu HTML
            MimeBodyPart htmlPart = new MimeBodyPart();
            String emailTemplate = "<html>" +
                    "<head>" +
                    "<style>" +
                    "body { font-family: Arial, sans-serif; background-color: #F4F7FA; text-align: center; padding: 40px; }" +
                    ".container { background: white; padding: 30px; border-radius: 12px; " +
                    "box-shadow: 0px 5px 20px rgba(0, 0, 0, 0.15); max-width: 520px; margin: auto; text-align: center; }" +
                    ".logo { width: 140px; margin-bottom: 15px; display: block; margin: 0 auto; }" +
                    "h2 { color: #132A3E; font-size: 22px; margin-bottom: 15px; }" +
                    "p { font-size: 16px; color: #444; line-height: 1.6; margin-bottom: 20px; }" +
                    ".cta-button { background-color: #E78D1E; color: white; font-size: 16px; " +
                    "text-decoration: none; padding: 12px 24px; border-radius: 8px; display: inline-block; " +
                    "margin-top: 20px; font-weight: bold; transition: 0.3s ease; }" +
                    ".cta-button:hover { background-color: #C6730C; }" +
                    ".footer { font-size: 12px; color: #888; margin-top: 25px; }" +
                    "</style>" +
                    "</head>" +
                    "<body>" +
                    "<div class='container'>" +
                    "<img class='logo' src='cid:logo' alt='TripToGo'>" +
                    "<h2>" + subject + "</h2>" +
                    "<p>" + messageContent + "</p>" +
                    "<p class='footer'>© 2025 TripToGo. Tous droits réservés.</p>" +
                    "</div>" +
                    "</body>" +
                    "</html>";


            htmlPart.setContent(emailTemplate, "text/html; charset=UTF-8");

            // 📌 Partie 2 : Ajouter l'image (logo)
            MimeBodyPart imagePart = new MimeBodyPart();
            DataSource fds = new FileDataSource(LOGO_PATH);
            imagePart.setDataHandler(new DataHandler(fds));
            imagePart.setHeader("Content-ID", "<logo>");
            imagePart.setDisposition(MimeBodyPart.INLINE);

            // 📌 Partie 3 : Regrouper les parties dans un Multipart
            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(htmlPart);
            multipart.addBodyPart(imagePart);

            message.setContent(multipart);

            // 📌 Envoi de l'email
            Transport.send(message);
            System.out.println("✅ Email envoyé avec succès !");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("❌ Une erreur est survenue lors de l'envoi de l'email.");
        }
    }

    // 📌 Exemple : Email de réinitialisation
    public static void sendPasswordResetEmail(String recipient, String code) {
        String subject = "Réinitialisation de votre mot de passe";
        String content = "<html>" +
                "<head>" +
                "<style>" +
                "body { font-family: Arial, sans-serif; background-color: #f4f4f4; text-align: center; padding: 40px; }" +
                ".container { background: white; padding: 30px; border-radius: 10px; " +
                "box-shadow: 0px 4px 15px rgba(0, 0, 0, 0.1); max-width: 500px; margin: auto; }" +
                "h2 { color: #132A3E; margin-bottom: 15px; }" +
                "p { font-size: 16px; color: #333; line-height: 1.6; margin-bottom: 15px; }" +
                ".code { font-size: 24px; font-weight: bold; color: #E78D1E; background: #FFF3E0; " +
                "padding: 10px 20px; display: inline-block; border-radius: 5px; margin: 15px 0; }" +
                ".footer { margin-top: 25px; font-size: 12px; color: #888; }" +
                "</style>" +
                "</head>" +
                "<body>" +
                "<div class='container'>" +
                "<p>Bonjour,</p>" +
                "<p>Vous avez demandé à réinitialiser votre mot de passe. Utilisez le code suivant :</p>" +
                "<p class='code'>" + code + "</p>"+
                "<p>Ce code est valide pour une durée limitée de 5 minutes.</p>" +
                "<p>Si vous n'avez pas fait cette demande, ignorez cet email.</p>" +
                "</div>" +
                "</body>" +
                "</html>";
        sendEmail(recipient, subject, content);
    }

    // 📌 Exemple : Email de bienvenue
    public static void sendWelcomeEmail(String recipient) {
        String subject = "Bienvenue chez TripToGo !";
        String content = "Nous sommes ravis de vous compter parmi nous.<br><br>" +
                "Profitez de nos services et explorez de nouvelles destinations.<br>" +
                "Bon voyage avec <b>TripToGo</b> !";
        sendEmail(recipient, subject, content);
    }


}
