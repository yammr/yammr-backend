package com.gibgab.service.security.verification;

import freemarker.template.Configuration;
import freemarker.template.Template;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

@RequiredArgsConstructor
public class VerificationEmailSender {

    private final int port;
    private final String emailSenderAddress;
    private final String emailSenderName;
    private final String host;
    private final String username;
    private final String password;
    private final String verificationURL;

    @Autowired
    private Configuration templates;

    public boolean sendVerificationMail(String toEmail, String verificationToken){
        String subject = "Please verify your email";
        String body = "";
        try{
            Template t = templates.getTemplate("email-verification.ftl");

            Map<String, String> map = new HashMap<>();
            map.put("VERIFICATION_URL", verificationURL + verificationToken);
            body = FreeMarkerTemplateUtils.processTemplateIntoString(t, map);
        } catch(Exception e) {
            return false;
        }

        return sendMail(toEmail, subject, body);
    }

    private boolean sendMail(String toEmail, String subject, String body) {
        try {
            Properties props = System.getProperties();
            props.put("mail.transport.protocol", "smtp");
            props.put("mail.smtp.port", port);
            props.put("mail.smtp.starttls.enable", "true");
            props.put("mail.smtp.auth", "true");

            Session session = Session.getDefaultInstance(props);
            session.setDebug(true);

            MimeMessage msg = new MimeMessage(session);
            msg.setFrom(new InternetAddress(emailSenderAddress, emailSenderName));
            msg.setRecipient(Message.RecipientType.TO, new InternetAddress(toEmail));
            msg.setSubject(subject);
            msg.setContent(body, "text/html");

            Transport transport = session.getTransport();
            transport.connect(host, username, password);
            transport.sendMessage(msg, msg.getAllRecipients());
        } catch (Exception e){
            throw new RuntimeException("Could not send email through SES", e);
        }

        return true;
    }
}
