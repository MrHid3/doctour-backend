package com.doctour.doctourbe.service;

import com.doctour.doctourbe.exception.AppointmentException;
import com.doctour.doctourbe.model.AppUser;
import com.doctour.doctourbe.model.Appointment;
import com.doctour.doctourbe.model.Location;
import com.doctour.doctourbe.repository.AppointentRepository;
import jakarta.transaction.Transactional;
import org.apache.logging.log4j.util.InternalException;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collections;
import java.util.List;

@Service
public class AppointmentService {

    private final AppointentRepository appointmentRepository;
    private final RoleService roleService;
    private final AvailabilityService availabilityService;

    public AppointmentService(AppointentRepository appointmentRepository, RoleService roleService, AvailabilityService availabilityService) {
        this.appointmentRepository = appointmentRepository;
        this.roleService = roleService;
        this.availabilityService = availabilityService;
    }

    public List<Appointment> getAll(AppUser user){
        if(user.getRoles().contains(roleService.findByName("DOCTOR").orElseThrow(() -> new InternalException("ROLE_BROKEN")))){
            return appointmentRepository.findAppointmentsByDoctor(user);
        }else if(user.getRoles().contains(roleService.findByName("CUSTOMER").orElseThrow(() -> new InternalException("ROLE_BROKEN")))){
            return appointmentRepository.findAppointmentsByCustomer(user);
        }

        return Collections.emptyList();
    }

    @Transactional
    public Appointment create(AppUser customer, AppUser doctor, LocalDate date, LocalTime start, LocalTime end, Location location){
        if(!appointmentRepository.findAppointmentsByCustomerAndDoctorAndDateAndEndAfterOrStartBefore(customer, doctor, date, start, end).isEmpty()){
            throw new AppointmentException("TAKEN");
        }
        if(!availabilityService.isAvailable(doctor, DayOfWeek.from(date), start, end, location)){
            throw new AppointmentException("NOT_AVAILABLE");
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
    public Appointment save(Appointment appointment){
        return appointmentRepository.save(appointment);
    }
}
