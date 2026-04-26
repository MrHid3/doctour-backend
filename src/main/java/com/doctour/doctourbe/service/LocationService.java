package com.doctour.doctourbe.service;

import com.doctour.doctourbe.model.Location;
import com.doctour.doctourbe.repository.LocationRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class LocationService {

    @Autowired
    private LocationRepository locationRepository;

    public List<Location> findAll(){
        return locationRepository.findAll();
    }

    @Transactional
    public Location createLocation(String name, String description, BigDecimal latitude, BigDecimal longitude, String city, String address, String postalCode) {
        Location location = new Location();
        location.setName(name);
        location.setDescription(description);
        location.setLatitude(latitude);
        location.setLongitude(longitude);
        location.setPostalCode(postalCode);
        location.setCity(city);
        location.setAddress(address);
        locationRepository.save(location);
        return location;
    }

    public Optional<Location> findById(Long id){
        return locationRepository.findById(id);
    }

    @Transactional
    public void save(Location location) {
        locationRepository.save(location);
    }

    @Scheduled(cron = "0 0 3 * * *")
    @Transactional
    protected void deleteUnused() {
        locationRepository.deleteUnused();
    }
}
