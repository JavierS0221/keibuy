package com.project.auction.service;

import com.project.auction.email.context.AbstractEmailContext;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.FileNotFoundException;

public interface EmailService {
     void sendMail(AbstractEmailContext email) throws MessagingException;
}
