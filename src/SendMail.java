import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;
import java.util.prefs.Preferences;

public class SendMail {
    private final String username;
    private final String password;
    private final String host;
    private final String port;
    private final boolean isGmail;
    private SendMail() throws ExceptionInInitializerError{
        Preferences prefs = Preferences.userNodeForPackage(MokTwona.class);
        username = prefs.get("mail", null);
        password = prefs.get("password", null);
        host = prefs.get("host", null);
        port = prefs.get("port", null);
        if (username == null || password == null || host == null || port == null)
            throw new ExceptionInInitializerError("Prefs pas initialisées ou pas atteignable");
        isGmail = username.split("@")[1].equals("gmail.com");
    }

    public static SendMail getSender() {
        try {
            return new SendMail();
        } catch (ExceptionInInitializerError e) {
            return null;
        }
    }

    private boolean sendTLS(String to, String subject, String messageText) {
        String destinataire = "julien.aigret@gmail.com";

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", port);

        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });

        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(destinataire));
            message.setSubject(subject);
            message.setText(messageText);

            Transport.send(message);
            return true;

        } catch (MessagingException e) {
            return false;
        }
    }

    public static void test() {

        String USER_NAME = "kotmanga@kapuclouvain.be";  // GMail user name (just the part before "@gmail.com")
        String PASSWORD = "kotmanga"; // GMail password
        String to = "julien.aigret@gmail.com";
        String from = USER_NAME;
        String pass = PASSWORD;

        String subject = "Test using";
        String body = "Ça marche ! âäàá êëéè îïíì ôöòó ûüùú ŷÿýỳ ÂÄÁÀ ÊËÉÈ ÎÏÍÌ ÔÖÓÒ ÛÜÚÙ ŶŸÝỲ";

        getSender().send(to,subject, body);

    }

    public boolean send(String to, String subject, String body) {
        if (isGmail) {
            return SendGMail.getSender().send(username, to, subject, body);
        }
        else {
            return sendTLS(to, subject, body);
        }
    }

    private void sendToGMail(String to, String subject, String body) {
        SendGMail.getSender().send(username, to, subject, body);
    }
}
