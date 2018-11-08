package com.gibgab.service.beans;

import com.gibgab.service.security.verification.VerificationEmailSender;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AmazonSESConfiguration {

    @Value("${amazon.ses.sender}")
    private String emailSenderAddress;

    @Value("${amazon.ses.smtp.port}")
    private int port;

    @Value("${amazon.ses.sender.name}")
    private String emailSenderName;

    @Value("${amazon.ses.smtp.password}")
    private String password;

    @Value("${amazon.ses.smtp.username}")
    private String username;

    @Value("${amazon.ses.smtp.host}")
    private String host;

    @Value("${pages.verification.url}")
    private String verificationURL;

    @Bean
    public VerificationEmailSender verificationEmailSender(){
        return new VerificationEmailSender(port, emailSenderAddress, emailSenderName, host, username, password, verificationURL);
    }
}
