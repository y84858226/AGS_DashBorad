package com.amazon.ags.commons;

import java.util.Date;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class SendEmail {
	public static void sendMail(String toMail, String mailContent, String subject) throws Exception {
		Properties props = new Properties();

		props.put("mail.smtp.host", "smtp.amazon.com");
		props.put("mail.smtp.auth", "true");
		System.out.println();
		Session session = Session.getInstance(props);
		// session.setDebug(true);

		MimeMessage message = new MimeMessage(session);
		message.setFrom(new InternetAddress("rbs ags tool <cn-flex-ags@amazon.com>"));
		String toMails[] = toMail.split(";");
		InternetAddress[] address = new InternetAddress[toMails.length];
		for (int i = 0; i < toMails.length; i++) {
			address[i] = new InternetAddress(toMails[i]);
		}
		message.setRecipients(Message.RecipientType.TO, address);

		message.setSubject(subject);

		// message.setText(mailContent); //send text
		message.setContent(mailContent, "text/html;charset=utf-8"); // sent html
		message.setSentDate(new Date());
		message.saveChanges();

		Transport transport = session.getTransport("smtp");
		// Transport transport = session.getTransport();
		transport.connect("rbs ags <cn-flex-ags@amazon.com>", "");
		transport.sendMessage(message, message.getAllRecipients());
		transport.close();
	}
}