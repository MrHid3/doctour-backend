package com.doctour.doctourbe.service;

import com.doctour.doctourbe.exception.AppointmentException;
import com.doctour.doctourbe.model.AppUser;
import com.doctour.doctourbe.model.Appointment;
import com.doctour.doctourbe.model.Location;
import com.doctour.doctourbe.repository.AppointentRepository;
import jakarta.transaction.Transactional;
import org.apache.logging.log4j.util.InternalException;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class AppointmentService {

    private final AppointentRepository appointmentRepository;
    private final RoleService roleService;
    private final AvailabilityService availabilityService;
    private final EmailSevice emailSevice;

    public AppointmentService(AppointentRepository appointmentRepository, RoleService roleService, AvailabilityService availabilityService, EmailSevice emailSevice) {
        this.appointmentRepository = appointmentRepository;
        this.roleService = roleService;
        this.availabilityService = availabilityService;
        this.emailSevice = emailSevice;
    }

    public List<Appointment> getAll(AppUser user){
        if(user.getRoles().contains(roleService.findByName("DOCTOR").orElseThrow(() -> new InternalException("ROLE_BROKEN")))){
            return appointmentRepository.findAppointmentsByDoctor(user);
        }else if(user.getRoles().contains(roleService.findByName("CUSTOMER").orElseThrow(() -> new InternalException("ROLE_BROKEN")))){
            return appointmentRepository.findAppointmentsByCustomer(user);
        }

        return Collections.emptyList();
    }

    public Optional<Appointment> findByUuid(UUID uuid){
        return appointmentRepository.findById(uuid);
    }

    @Transactional
    public Appointment create(AppUser customer, AppUser doctor, LocalDate date, LocalTime start, LocalTime end, Location location){
        if(!appointmentRepository.findAppointmentsByCustomerAndDoctorAndDateAndEndAfterOrStartBeforeOrStartIsOrEndIs(customer, doctor, date, start, end, start, end).isEmpty()){
            throw new AppointmentException("TAKEN");
        }
        if(!availabilityService.isAvailable(doctor, DayOfWeek.from(date), start, end, location)){
            throw new AppointmentException("NOT_AVAILABLE");
        }
        if(start.isBefore(end)){
            throw new AppointmentException("START_BEFORE_END");
        }
        Appointment ap = new Appointment();
        ap.setCustomer(customer);
        ap.setDoctor(doctor);
        ap.setLocation(location);
        ap.setDate(date);
        ap.setStart(start);
        ap.setEnd(end);
        return this.save(ap);
    }

    @Transactional
    public void delete(AppUser deleter, Appointment ap){
        if(!ap.getCustomer().equals(deleter) && !ap.getDoctor().equals(deleter)){
            throw new AppointmentException("NO_ACCESS");
        }
        appointmentRepository.delete(ap);
        appointmentRepository.flush();
    }

    @Scheduled(cron = "0 0 12 * * * ")
    protected void sendRemainders(){
        List<Appointment> aps = appointmentRepository.findByDate(LocalDate.now().plusDays(1));
        for(Appointment ap : aps){
            emailSevice.sendReminder(
        }
    }

    @Transactional
    public Appointment save(Appointment appointment){
        return appointmentRepository.save(appointment);
    }
}
