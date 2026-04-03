
//  1. Sending email using SMTP service....

//package com.Project.PlacementCell.Service.Auth;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.mail.SimpleMailMessage;
//import org.springframework.mail.javamail.JavaMailSender;
//import org.springframework.stereotype.Service;
//import org.springframework.web.client.RestTemplate;
//
//@Service
//public class EmailService {
//    @Autowired
//    private JavaMailSender mailSender;
//
//    public void sendRegistrationMail(String toEmail,
//                                     String username,
//                                     String tempPassword) {
//
//        SimpleMailMessage message = new SimpleMailMessage();
//        message.setTo(toEmail);
//        message.setSubject("Placement Cell Login credentials");
//
//        message.setText(
//                "Hello " + username + ",\n\n" +
//                        "Your Placement Cell account has been created.\n\n" +
//                        "Email: " + toEmail + "\n" +
//                        "Temporary Password: " + tempPassword + "\n\n" +
//                        "Please login and change your password immediately.\n\n" +
//                        "Thank you."
//        );
//
//        mailSender.send(message);
//    }
//    public void sendEmail(String to, String subject, String body) {
//
//        SimpleMailMessage message = new SimpleMailMessage();
//        message.setTo(to);
//        message.setSubject(subject);
//        message.setText(body);
//
//        mailSender.send(message);
//    }
//}



// 2. Sending email using BREVO email service....

package com.Project.PlacementCell.Service.Auth;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@Service
public class EmailService {

    @Value("${brevo.api.key}")
    private String brevoKey;

    @Value("${brevo.url}")
    private String brevoURL;

    @Value("${brevo.sender.email:nimprajapati2004@gmail.com}")
    private String senderEmail;

    @Value("${brevo.sender.name:Placement Cell}")
    private String senderName;

    // Direct instantiation works perfectly fine for this use case
    private final RestTemplate restTemplate = new RestTemplate();

    public void sendRegistrationMail(String toEmail, String username, String tempPassword) {
        String subject = "Placement Cell Login credentials";
        String body = "Hello " + username + ",\n\n" +
                "Your Placement Cell account has been created.\n\n" +
                "Email: " + toEmail + "\n" +
                "Temporary Password: " + tempPassword + "\n\n" +
                "Please login and change your password immediately.\n\n" +
                "Thank you.";

        // Pass the data to the Brevo helper
        sendBrevoEmail(toEmail, subject, body);
    }

    public void sendEmail(String to, String subject, String body) {
        // Pass the data to the Brevo helper
        sendBrevoEmail(to, subject, body);
    }

    private void sendBrevoEmail(String toEmail, String subject, String textContent) {
        // 1. Set the headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));
        headers.set("api-key", brevoKey);

        // 2. Build the JSON body
        Map<String, Object> payload = Map.of(
                "sender", Map.of("name", senderName, "email", senderEmail),
                "to", List.of(Map.of("email", toEmail)),
                "subject", subject,
                "textContent", textContent
        );

        // 3. Create the request entity
        HttpEntity<Map<String, Object>> request = new HttpEntity<>(payload, headers);

        // 4. Fire the request
        try {
            restTemplate.postForEntity(brevoURL, request, String.class);
            System.out.println("Email successfully sent to " + toEmail);
        } catch (Exception e) {
            System.err.println("Brevo email failed to send: " + e.getMessage());
        }
    }
}