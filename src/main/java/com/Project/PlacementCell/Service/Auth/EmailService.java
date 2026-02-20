package com.Project.PlacementCell.Service.Auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
    @Autowired
    private JavaMailSender mailSender;

    public void sendRegistrationMail(String toEmail,
                                     String username,
                                     String tempPassword) {

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toEmail);
        message.setSubject("Placement Cell Login Details");

        message.setText(
                "Hello " + username + ",\n\n" +
                        "Your Placement Cell account has been created.\n\n" +
                        "Username: " + username + "\n" +
                        "Temporary Password: " + tempPassword + "\n\n" +
                        "Please login and change your password immediately.\n\n" +
                        "Thank you."
        );

        mailSender.send(message);
    }
}
