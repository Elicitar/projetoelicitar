package mail;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class SendMails {

	public static void enviarEmail(String de, String para, String assunto, String texto) {
		final String username = "projetoelicitar@gmail.com";
		final String password = "3l3fante";

		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");

		Session session = Session.getInstance(props,
				new javax.mail.Authenticator() {
					protected PasswordAuthentication getPasswordAuthentication() {
						return new PasswordAuthentication(username, password);
					}
				});

		try {

			Message message = new MimeMessage(session);
			if(de == ""){
				de = "projetoelicitar@gmail.com";
			}
			message.setFrom(new InternetAddress(de));
			message.setRecipients(Message.RecipientType.TO,
					InternetAddress.parse(para));
			message.setSubject(assunto);
			message.setText(texto);

			Transport.send(message);

		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
	}
}
