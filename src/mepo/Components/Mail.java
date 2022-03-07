package mepo.Components;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Properties;
import java.util.Random;

public class Mail {
    public String doEmailVerification(String email) throws MessagingException, UnsupportedEncodingException {
        String randomNumber = getRandomNumberString();
        String body = "Welcome to MEPO CINEMA, your email verification code is: " + randomNumber;
        this.sendEmail(email, "Email Verification Mepo", body);
        return randomNumber;
    }
    public String resetPw(String email) throws MessagingException, UnsupportedEncodingException {
        String randomNumber = getRandomNumberString();
        String body = "Welcome to MEPO CINEMA, your new password is: " + randomNumber;
        this.sendEmail(email, "Reset Password", body);
        return randomNumber;
    }

    private  String getRandomNumberString() {
        Random rnd = new Random();
        int number = rnd.nextInt(999999);
        return String.format("%06d", number);
    }

    private void sendEmail(String toEmail, String subject, String body) throws UnsupportedEncodingException, MessagingException {
        final String fromEmail = "mepocinemavietnam@gmail.com";
        final String password = "123456789!@#";


        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com"); //SMTP Host
        props.put("mail.smtp.port", "587"); //TLS Port
        props.put("mail.smtp.auth", "true"); //enable authentication
        props.put("mail.smtp.starttls.enable", "true"); //enable STARTTLS

        Authenticator auth = new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(fromEmail, password);
            }
        };
        Session session = Session.getInstance(props, auth);


        MimeMessage msg = new MimeMessage(session);
        //set message headers
        msg.addHeader("Content-type", "text/HTML; charset=UTF-8");
        msg.addHeader("format", "flowed");
        msg.addHeader("Content-Transfer-Encoding", "8bit");

        msg.setFrom(new InternetAddress(fromEmail, "MEPO CINEMA"));

        msg.setReplyTo(InternetAddress.parse(fromEmail, false));

        msg.setSubject(subject, "UTF-8");

        msg.setText(body, "UTF-8");

        msg.setSentDate(new Date());

        msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail, false));
        Transport.send(msg);
    }
}
