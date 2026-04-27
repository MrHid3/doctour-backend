package com.doctour.doctourbe.service;

import com.doctour.doctourbe.exception.AppointmentException;
import com.doctour.doctourbe.model.AppUser;
import com.doctour.doctourbe.model.Appointment;
import com.doctour.doctourbe.model.Location;
import com.doctour.doctourbe.repository.AppointmentRepository;
import jakarta.transaction.Transactional;
import org.apache.logging.log4j.util.InternalException;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

@Service
public class AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final RoleService roleService;
    private final AvailabilityService availabilityService;
    private final EmailSevice emailService;

    public AppointmentService(AppointmentRepository appointmentRepository, RoleService roleService, AvailabilityService availabilityService, EmailSevice emailService) {
        this.appointmentRepository = appointmentRepository;
        this.roleService = roleService;
        this.availabilityService = availabilityService;
        this.emailService = emailService;
    }

    public List<Appointment> getAll(AppUser user) {
        if (user.getRoles().contains(roleService.findByName("ROLE_DOCTOR").orElseThrow(() -> new InternalException("ROLE_BROKEN")))) {
            return appointmentRepository.findAppointmentsByDoctor(user);
        } else if (user.getRoles().contains(roleService.findByName("ROLE_CUSTOMER").orElseThrow(() -> new InternalException("ROLE_BROKEN")))) {
            return appointmentRepository.findAppointmentsByCustomer(user);
        }

        return Collections.emptyList();
    }

    public Optional<Appointment> findByUuid(UUID uuid) {
        return appointmentRepository.findById(uuid);
    }

    @Transactional
    public Appointment create(AppUser customer, AppUser doctor, LocalDate date, LocalTime start, LocalTime end, Location location) {
        Collection<Appointment> aps = appointmentRepository.findAppointmentsByDoctorAndDate(doctor, date);
        for (Appointment ap : aps) {
            if (
                    !((ap.getStartTime().isAfter(start) && ap.getEndTime().isAfter(end))
                            || (ap.getStartTime().isBefore(end) && ap.getEndTime().isBefore(start)))) {
                throw new AppointmentException("TAKEN");
            }
        }
        if (!availabilityService.isAvailable(doctor, DayOfWeek.from(date), start, end, location)) {
            throw new AppointmentException("NOT_AVAILABLE");
        }
        if (end.isBefore(start)) {
            throw new AppointmentException("END_BEFORE_START");
        }
        if (end.equals(start)) {
            throw new AppointmentException("START_IS_END");
        }

        Appointment ap = new Appointment();
        ap.setCustomer(customer);
        ap.setDoctor(doctor);
        ap.setLocation(location);
        ap.setDate(date);
        ap.setStartTime(start);
        ap.setEndTime(end);
        return this.save(ap);
    }

    @Transactional
    public void delete(AppUser deleter, Appointment ap) {
        if (!ap.getCustomer().equals(deleter) && !ap.getDoctor().equals(deleter)) {
            throw new AppointmentException("NO_ACCESS");
        }
        appointmentRepository.delete(ap);
        appointmentRepository.flush();
    }

    @Transactional
    public Appointment update(AppUser updater, Appointment ap, LocalDate date, LocalTime start, LocalTime end){
        LocalDate originalDate = ap.getDate();
        LocalTime originalStartTime = ap.getStartTime();
        LocalTime originalEndTime = ap.getEndTime();
        if(originalDate.equals(date) && originalStartTime.equals(start) && originalEndTime.equals(end)){
            throw new AppointmentException("NOT_CHANGED");
        }
        if(date == null){
            date = ap.getDate();
        }
        if(start == null){
            start = ap.getStartTime();
        }
        if(end == null){
            end = ap.getEndTime();
        }
        if (!ap.getCustomer().equals(updater) && !ap.getDoctor().equals(updater)) {
            throw new AppointmentException("NO_ACCESS");
        }
        if (!availabilityService.isAvailable(ap.getDoctor(), DayOfWeek.from(date), start, end, ap.getLocation())) {
            throw new AppointmentException("NOT_AVAILABLE");
        }
        Collection<Appointment> aps = appointmentRepository.findAppointmentsByDoctorAndDate(ap.getDoctor(), date);
        for (Appointment appointment : aps) {
            if (
                    !((appointment.getStartTime().isAfter(start) && appointment.getEndTime().isAfter(end))
                            || (appointment.getStartTime().isBefore(end) && appointment.getEndTime().isBefore(start)))) {
                throw new AppointmentException("TAKEN");
            }
        }
        ap.setDate(date);
        ap.setStartTime(start);
        ap.setEndTime(end);
        this.save(ap);
        if(updater.getRoles().contains(roleService.findByName("ROLE_DOCTOR").get())){
            emailService.sendMovedNotification(ap, originalDate, originalStartTime, originalEndTime);
        }
        return ap;
    }

    @Scheduled(cron = "0 0 12 * * * ")
//    @Scheduled(cron = "0 * * * * *") //for demo
    public void sendRemainders() {
        List<Appointment> aps = appointmentRepository.findByDate(LocalDate.now().plusDays(1));
        for (Appointment ap : aps) {
            emailService.sendReminder(ap);
        }
    }

    @Transactional
    public Appointment save(Appointment appointment) {
        return appointmentRepository.save(appointment);
    }
}
