package com.doctour.doctourbe.service;

import com.doctour.doctourbe.dto.LocationDTO;
import com.doctour.doctourbe.exception.AppUserException;
import com.doctour.doctourbe.exception.AppointmentException;
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
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.apache.logging.log4j.ThreadContext.isEmpty;

@Service
public class AvailabilityService {

    @Autowired
    private AvailabilityRepository availabilityRepository;

    @Autowired
    private RoleService roleService;
    @Autowired
    private AppUserService appUserService;

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
        List<Availability> avs = availabilityRepository.findAvailabilitiesByAppUserAndDayOfWeekAndLocation(doctor, dayOfWeek, location);
        for(Availability av : avs){
            if((av.getStartTime().equals(start) || av.getStartTime().isBefore(start))
                    && (av.getEndTime().equals(end)|| av.getEndTime().isAfter(end))){
                return true;
            }
        }
        return false;
    }

    public List<Availability> findInLocation(Location location){
        return availabilityRepository.findAvailabilitiesByLocationIs(location);
    }

    @Transactional
    public Availability create(AppUser appUser, Location location, DayOfWeek day, LocalTime start, LocalTime end){
        Collection<Availability> availabilities =  availabilityRepository.findAvailabilitiesByAppUserAndDayOfWeek(appUser, day);
        for(Availability av : availabilities){
            if(
                    !((av.getStartTime().isAfter(start) && av.getEndTime().isAfter(end))
                    || (av.getStartTime().isBefore(end) && av.getEndTime().isBefore(start)))){
                throw new AvailabilityException("TAKEN");
            }
        }
        if(end.isBefore(start)){
            throw new AvailabilityException("END_BEFORE_START");
        }
        if(end.equals(start)){
            throw new AvailabilityException("START_IS_END");
        }

        Availability av = new Availability();
        av.setAppUser(appUser);
        av.setLocation(location);
        av.setStartTime(start);
        av.setEndTime(end);
        av.setDayOfWeek(day);

        this.save(av);
        appUserService.addAvailability(av.getAppUser(), av);
        return av;
    }

    @Transactional
    public Availability update(Availability availability, Location location, LocalTime start, LocalTime end){
        if(location != null){
            availability.setLocation(location);
        }
        if(start != null){
            availability.setStartTime(start);
        }
        if(end != null) {
            availability.setEndTime(end);
        }
        return this.save(availability);
    }

    @Transactional
    public Availability save(Availability av){
        return availabilityRepository.save(av);
    }

    @Transactional
    public void delete(Availability av){
        appUserService.removeAvailability(av.getAppUser(), av);
        availabilityRepository.delete(av);
        availabilityRepository.flush();
    }

}
