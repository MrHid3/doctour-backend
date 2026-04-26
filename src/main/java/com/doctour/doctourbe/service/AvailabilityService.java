package com.doctour.doctourbe.service;

import com.doctour.doctourbe.exception.AppUserException;
import com.doctour.doctourbe.exception.AvailabilityException;
import com.doctour.doctourbe.model.AppUser;
import com.doctour.doctourbe.model.Availability;
import com.doctour.doctourbe.model.Location;
import com.doctour.doctourbe.repository.AvailabilityRepository;
import jakarta.transaction.Transactional;
import org.apache.logging.log4j.util.InternalException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class AvailabilityService {

    @Autowired
    private AvailabilityRepository availabilityRepository;

    @Autowired
    private RoleService roleService;

    public Optional<Availability> findByUuid(UUID uuid){
        return availabilityRepository.findByUuid(uuid);
    }

    public List<Availability> findByDoctor(AppUser doctor){
        if(!doctor.getRoles().contains(roleService.findByName("ROLE_DOCTOR").orElseThrow(() -> new InternalException("ROLE_BROKEN")))){
            throw new AppUserException("NOT_A_DOCTOR");
        }
        return availabilityRepository.findByAppUser(doctor);
    }

    public boolean isAvailable(AppUser doctor, DayOfWeek dayOfWeek, LocalTime start, LocalTime end, Location location){
        return availabilityRepository.findAvailabilitiesByAppUserAndDayOfWeekAndStartBeforeAndEndAfterAndLocation(doctor, dayOfWeek, start, end, location).isEmpty();
    }

    @Transactional
    public Availability create(AppUser appUser, Location location, DayOfWeek day, LocalTime start, LocalTime end){
        if(!availabilityRepository.findAvailabilitiesByAppUserAndDayOfWeekAndStartAfterOrEndBefore(appUser, day, start, end).isEmpty()){
            throw new AvailabilityException("TAKEN");
        }
        Availability av = new Availability();
        av.setAppUser(appUser);
        av.setLocation(location);
        av.setStart(start);
        av.setEnd(end);
        return this.save(av);
    }

    @Transactional
    public Availability update(Availability availability, Location location, LocalTime start, LocalTime end){
        if(location != null){
            availability.setLocation(location);
        }
        if(start != null){
            availability.setStart(start);
        }
        if(end != null) {
            availability.setEnd(end);
        }
        return this.save(availability);
    }

    @Transactional
    public Availability save(Availability av){
        return availabilityRepository.save(av);
    }

    @Transactional
    public void delete(Availability av){
        availabilityRepository.delete(av);
        availabilityRepository.flush();
    }
}
