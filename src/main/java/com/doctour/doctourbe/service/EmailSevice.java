package com.doctour.doctourbe.service;

import com.doctour.doctourbe.model.AppUser;
import com.doctour.doctourbe.model.Appointment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.time.LocalDate;
import java.time.LocalTime;

@Service
public class EmailSevice {

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private TemplateEngine templateEngine;

    @Value("${app.frontend-url}")
    private String frontendUrl;

    @Value("${spring.mail.username}")
    private String fromAdress;

    @Value("${app.magic-link.expiry-hours}")
    private Integer expiryHours;

    public void sendActiviationLink(AppUser appUser, String token) {

        String link = frontendUrl + "/activateAccount?token=" + token;

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

    public void sendResetLink(AppUser appUser, String token) {

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

    public void sendMovedNotification(Appointment appointment, LocalDate originalDate, LocalTime originalStart, LocalTime originalEnd){
        String link = frontendUrl + "/appointment/" + appointment.getUuid().toString();

        Context context = new Context();
        context.setVariable("link", link);

        context.setVariable("originalDate", originalDate.toString() + " od " + originalStart + " do " +originalEnd);
        context.setVariable("newDate", appointment.getDate() + " od " + appointment.getStartTime() + " do " + appointment.getEndTime());

        String html = templateEngine.process("email/reminder", context);

        MimeMessagePreparator preparator = msg -> {
            MimeMessageHelper helper = new MimeMessageHelper(msg, true, "UTF-8");
            helper.setFrom(fromAdress);
            helper.setTo(appointment.getCustomer().getEmail());
            helper.setSubject("Zmiana terminu wizyty");
            helper.setText(html, true);
        };

        mailSender.send(preparator);
    }

    public void sendReminder(Appointment appointment) {

        String link = frontendUrl + "/appointment/" + appointment.getUuid().toString();

        Context context = new Context();
        context.setVariable("link", link);

        context.setVariable("countdownGifUrl", "https://i.countdownmail.com/4zzdo3.gif?end_date_time="
                + appointment.getDate().toString() + "T" + appointment.getStartTime().toString() + ":00+00:00");

        String html = templateEngine.process("email/reminder", context);

        MimeMessagePreparator preparator = msg -> {
            MimeMessageHelper helper = new MimeMessageHelper(msg, true, "UTF-8");
            helper.setFrom(fromAdress);
            helper.setTo(appointment.getCustomer().getEmail());
            helper.setSubject("Przypomnienie o wizycie");
            helper.setText(html, true);
        };

        mailSender.send(preparator);
    }
}
