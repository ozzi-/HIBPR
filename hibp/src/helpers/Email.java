package helpers;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.util.ByteArrayDataSource;

import com.eclipsesource.json.Json;
import com.eclipsesource.json.JsonArray;
import com.eclipsesource.json.JsonValue;

import hibp.HIBP;

public class Email {

	public static boolean sendToRecipients(String vulnHTML) {
		InputStream stream = null;
		try {
			stream = HIBP.class.getClass().getResourceAsStream("/email.json");
			String emailjsonString = Persistence.convertStreamToString(stream);
			stream.close();
			JsonValue json = Json.parse(emailjsonString);
			JsonArray recipientsElement = json.asObject().get("recipients").asArray();
			JsonValue senderElement = json.asObject().get("sender");
			String address = senderElement.asObject().get("address").asString();
			String host = senderElement.asObject().get("host").asString();
			int port = senderElement.asObject().get("port").asInt();
			String user = senderElement.asObject().get("user").asString();
			String password = senderElement.asObject().get("password").asString();
			String subject = senderElement.asObject().get("subject").asString();
			subject = subject.replace("<d>", new SimpleDateFormat("dd-MM-yyyy").format(new Date()));
			
			for (JsonValue recipient : recipientsElement) {
				send(recipient.asString(),address, subject,"Please find the report attached as HTML", vulnHTML, "report.html", host, port, user, password);
			}
			return true;
		} catch (Exception e) {
			System.out.print("Error loading email.json ");
			System.out.println(e.getMessage());
		}
		return false;
	}

	private static void send(String to, String from, String subject, String messageText, String attachment, String attachmentName, String mailHost, int port, String user, String password) {
		Properties properties = System.getProperties();
		properties.setProperty("mail.smtp.host", mailHost);
		properties.setProperty("mail.smtp.auth", "true");
		properties.setProperty("mail.user", user);
		properties.setProperty("mail.password", password);
		properties.setProperty("mail.smtp.port", String.valueOf(port));
		properties.setProperty("mail.smtp.starttls.enable", "true");
		System.out.println("sending mail from "+from+" to "+to+" with subject "
							+subject+" via server "+mailHost+":"+port+" and user "+user);
	    Session session = Session.getInstance(properties, new SmtpAuthenticator(user, password));
	       
		try {
			MimeMessage message = new MimeMessage(session);
			message.setFrom(new InternetAddress(from));
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
			message.setSubject(subject);
			message.setText(messageText);

		    BodyPart messageBodyPart = new MimeBodyPart();
		    messageBodyPart.setText(messageText);
		    Multipart multipart = new MimeMultipart();
		    multipart.addBodyPart(messageBodyPart);
		    messageBodyPart = new MimeBodyPart();
		    DataSource source = new ByteArrayDataSource(attachment, "text/html; charset=UTF-8");
		    messageBodyPart.setDataHandler(new DataHandler(source));
		    messageBodyPart.setFileName(attachmentName);
		    multipart.addBodyPart(messageBodyPart);
		    message.setContent(multipart);

			Transport.send(message);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
        
	}
}

