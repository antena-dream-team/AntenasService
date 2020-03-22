package br.gov.sp.fatec.utils.commons;

import br.gov.sp.fatec.utils.exception.Exception.SendEmailFailedException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.Date;

@Service
@EnableAsync
public class SendEmail {

    @Autowired
    private JavaMailSender javaMailSender;

    @Async
    public void sendMail(String email, String module) {

        try {
            JSONObject base64 = new JSONObject();
            base64.put("dateTime", new Date());
            base64.put("email", email);

            String b64 = Base64.getEncoder().encodeToString(base64.toString().getBytes());
            String link = "http://127.0.0.1:8080/dev/" + module + "/activate/" + b64;

            var mailMessage = new SimpleMailMessage();

            mailMessage.setTo(email);
            mailMessage.setSubject("Antenas - Confirmação de conta");
            mailMessage.setText("Para confirmar sua conta, clique no link: " + link);

            mailMessage.setFrom("sendEmailMD@gmail.com");

            javaMailSender.send(mailMessage);
        } catch (Exception ex) {
            throw new SendEmailFailedException();
        }
    }

//    public void sendMail(String toEmail, String subject, String message) {
//
//        var mailMessage = new SimpleMailMessage();
//
//        mailMessage.setTo(toEmail);
//        mailMessage.setSubject(subject);
//        mailMessage.setText(message);
//
//        mailMessage.setFrom("sendEmailMD@gmail.com");
//
//        javaMailSender.send(mailMessage);
//    }
//    @Autowired
//    MailSender mailSender;
//
//    public String sendMail() {
//        SimpleMailMessage message = new SimpleMailMessage();
//        message.setText("Hello from Spring Boot Application");
//        message.setTo("daniellygj@gmail.com");
////        message.setFrom("sendEmailMD@gmail.com");
////        message.setA
//        try {
//            mailSender.send(message);
//            return "{\"message\": \"OK\"}";
//        } catch (Exception e) {
//            e.printStackTrace();
//            return "{\"message\": \"Error\"}";
//        }
//    }

//    @Autowired
//    private static JavaMailSender mailSender;
//
//    public static String sendMail() {
//        SimpleMailMessage message = new SimpleMailMessage();
//        message.setText("Hello from Spring Boot Application");
//        message.setTo("daniellygj@gmail.com");
//        message.setFrom("daniellygj@gmail.com");
//        message.setSubject("Email de ativação");
//
//        try {
//            mailSender.send(message);
//            return("Email enviado com sucesso!");
//        } catch (Exception e) {
//            e.printStackTrace();
//            return("Erro ao enviar email.");
//        }
//    }
}
