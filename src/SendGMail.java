import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.gmail.Gmail;
import com.google.api.services.gmail.model.Message;
import org.apache.commons.codec.binary.Base64;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.ByteArrayOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Properties;


public class SendGMail {
    private String apiKey;
    private String apiSecret;
    private String accessToken;
    private String refreshToken;

    private SendGMail() throws IOException, ParseException {
        JSONParser parser = new JSONParser();
        // A JSON object. Key value pairs are unordered. JSONObject supports java.util.Map interface.
        JSONObject jsonObject = (JSONObject) parser.parse(new FileReader("credentials.json"));

        // A JSON array. JSONObject supports java.util.List interface.
        apiKey = (String) jsonObject.get("client_id");
        apiSecret = (String) jsonObject.get("client_secret");
        accessToken = (String) jsonObject.get("token");
        refreshToken = (String) jsonObject.get("refresh_token");
    }

    public static SendGMail getSender() {
        try {
            SendGMail sgm = new SendGMail();
            return sgm;
        } catch (IOException e) {
            return null;
        } catch (ParseException e) {
            return null;
        }
    }

    /**
     * Create a Message from an email
     *
     * @param email Email to be set to raw of message
     * @return Message containing base64url encoded email.
     * @throws IOException
     * @throws MessagingException
     */
    public static Message createMessageWithEmail(MimeMessage email)
            throws MessagingException, IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        email.writeTo(baos);
        String encodedEmail = Base64.encodeBase64URLSafeString(baos.toByteArray());
        Message message = new Message();
        message.setRaw(encodedEmail);
        return message;
    }
    private static MimeMessage createEmail(String to, String from, String subject, String bodyText) throws MessagingException {
        Properties props = new Properties();
        Session session = Session.getDefaultInstance(props, null);
        MimeMessage email = new MimeMessage(session);
        email.setFrom(new InternetAddress(from));
        email.addRecipient(javax.mail.Message.RecipientType.TO, new InternetAddress(to));
        email.setSubject(subject);
        email.setText(bodyText);
        return email;
    }


    private static Gmail performRequest(String accessToken, String refreshToken, String apiKey, String apiSecret) throws GeneralSecurityException, IOException, MessagingException {
        HttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();
        JsonFactory jsonFactory = new JacksonFactory();
        final Credential credential = convertToGoogleCredential(accessToken, refreshToken, apiSecret, apiKey);
        Gmail.Builder builder = new Gmail.Builder(httpTransport, jsonFactory, credential);
        builder.setApplicationName("MokTwona");
        return builder.build();
    }

    private static Credential convertToGoogleCredential(String accessToken, String refreshToken, String apiSecret, String apiKey) {
        HttpTransport httpTransport = new NetHttpTransport();
        JsonFactory jsonFactory = new JacksonFactory();
        GoogleCredential credential = new GoogleCredential.Builder().setTransport(httpTransport).setJsonFactory(jsonFactory).setClientSecrets(apiKey, apiSecret).build();
        credential.setAccessToken(accessToken);
        credential.setRefreshToken(refreshToken);
        try {
            credential.refreshToken();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return credential;
    }

    public boolean send(String from, String to, String subject, String bodyText) {
        try {
            // these are either retrieved
            Gmail gmail = performRequest(accessToken, refreshToken, apiKey, apiSecret);
            MimeMessage content = createEmail(to, from, subject, bodyText);
            Message message = createMessageWithEmail(content);
            gmail.users().messages().send(from, message).execute();
            System.out.println("Message envoyé à " + to);
        } catch (GeneralSecurityException e) {
            return false;
        } catch (IOException e) {
            return false;
        } catch (MessagingException e) {
            return false;
        }
        return true;
    }

}
