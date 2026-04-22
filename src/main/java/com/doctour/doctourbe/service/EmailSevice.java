package com.doctour.doctourbe.service;

import com.doctour.doctourbe.model.AppUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Service
public class EmailSevice {

    private JavaMailSender mailSender;
    private TemplateEngine templateEngine;

    @Value("${app.frontend-url}")
    private String frontendUrl;

    @Value("${spring.mail.username}")
    private String fromAdress;

    @Value("${app.magic-link.expiry-hours}")
    private Integer expiryHours;

    public void sendInviteLink(AppUser appUser, String token){

        String link = frontendUrl + "/set-password?token=" + token;

        Context context = new Context();
        context.setVariable("link", link);
        context.setVariable("expiryHours", expiryHours);

        String html = templateEngine.process("email/registration", context);

        MimeMessagePreparator preparator = msg -> {
            MimeMessageHelper helper = new MimeMessageHelper(msg, true, "UTF-8");
            helper.setFrom(fromAdress);
            helper.setTo(appUser.getEmail());
            helper.setSubject("Aktywacja konta doctour");
            helper.setText(html, true);
        };

        mailSender.send(preparator);
    }

    public void sendResetLink(AppUser appUser, String token){

        String link = frontendUrl + "/reset-password?token=" + token;

        Context context = new Context();
        context.setVariable("link", link);
        context.setVariable("expiryHours", expiryHours);

        String html = templateEngine.process("email/reset-password", context);

        MimeMessagePreparator preparator = msg -> {
            MimeMessageHelper helper = new MimeMessageHelper(msg, true, "UTF-8");
            helper.setFrom(fromAdress);
            helper.setTo(appUser.getEmail());
            helper.setSubject("Reset hasła (doctour)");
            helper.setText(html, true);
        };

        mailSender.send(preparator);
    }
}
