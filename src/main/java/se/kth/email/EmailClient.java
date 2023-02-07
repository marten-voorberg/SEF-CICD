package se.kth.email;

import se.kth.github.StatusState;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;

public class EmailClient {
    private final String password;
    public EmailClient() {
        try {
            this.password = Files.readString(Path.of("secrets/email_password"));
        } catch (IOException e) {
            System.err.println("No password file was found! Make sure you have a /secrets/email_password file");
            throw new RuntimeException(e);
        }
    }

    /**
     * This Function send an CI Result email to the committer using a gmail account
     * The password of this email account should be saved in a plain text file locate at "secrets/email_password"
     * @param receiver the email address of the receiver
     * @param state the build status state Code
     * @param description Detailed description about the build
     */
    public void sendResultEmail(String receiver, StatusState state, String description){
        // Sender's email ID
        String from = "sefcicd.group4@gmail.com";
        // Sending email through Gmails smtp
        String host = "smtp.gmail.com";
        // Get system properties
        Properties properties = System.getProperties();
        // Setup mail server
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", "465");
        properties.put("mail.smtp.ssl.enable", "true");
        properties.put("mail.smtp.auth", "true");
        // Get the Session object.// and pass username and password
        Session session = Session.getInstance(properties, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(from, password);
            }
        });

        // Used to debug SMTP issues
        session.setDebug(true);

        try {
            // Create a default MimeMessage object.
            MimeMessage message = new MimeMessage(session);
            // Set From: header field of the header.
            message.setFrom(new InternetAddress(from));
            // Set To: header field of the header.
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(receiver));
            // Set Subject: header field
            message.setSubject("Commit Status changed to: " + state.toString());
            // Now set the actual message
            message.setText(description);
            System.out.println("sending...");
            // Send message
            Transport.send(message);
            System.out.println("Sent message successfully....");
        } catch (MessagingException mex) {
            mex.printStackTrace();
        }
    }
}
