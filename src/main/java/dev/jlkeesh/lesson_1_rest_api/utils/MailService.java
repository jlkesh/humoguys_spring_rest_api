package dev.jlkeesh.lesson_1_rest_api.utils;


import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMailMessage;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class MailService{
    private final JavaMailSender emailSender;

    @Async
    public void sendActivationMail(@NonNull Map<String, String> model) {
        try {
            MimeMessage message = emailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(model.get("from"));
            helper.setTo(model.get("to"));
            helper.setSubject(model.get("subject"));
            helper.setText("""
                    <!DOCTYPE html>
                    <html lang="en">
                    <head>
                        <meta charset="UTF-8">
                        <title>BlogApp Activation</title>
                        <style>
                            body {
                                margin: 0;
                                background-color: darkorange;
                            }
                                        
                            h1 {
                                text-align: center;
                                font-size: 70px;
                                color: white;
                                font-family: arial;
                                text-transform: uppercase;
                            }
                            p{
                                text-align: center;
                                font-size: 24px;
                                color: white;
                                font-weight: bold;
                            }
                        </style>
                                        
                    </head>
                    <body>
                    <h1>Welcome to BlogApp</h1>
                    <p>
                        According to activate your account please click <a href="http://localhost:9090/api/auth/activate/%s">here</a>
                    </p>
                    </body>
                    </html>
                    """.formatted(model.get("code")), true);
            emailSender.send(message);
        } catch (MessagingException ignored) {
            ignored.printStackTrace();
        }
    }

}
