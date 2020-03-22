package br.gov.sp.fatec.utils.commons;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class SendEmail {
    @Autowired
    private JavaMailSender javaMailSender;

//    public SendEmail(JavaMailSender javaMailSender) {
//        this.javaMailSender = javaMailSender;
//    }

    public void sendMail() {

        var mailMessage = new SimpleMailMessage();

        mailMessage.setTo("daniellygj@gmail.com");
        mailMessage.setSubject("daniellygj@gmail.com");
        mailMessage.setText("ndliasldnaldkaslkdska");

        mailMessage.setFrom("sendEmailMD@gmail.com");

        javaMailSender.send(mailMessage);
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
