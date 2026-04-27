package com.doctour.doctourbe.service;

import com.doctour.doctourbe.model.Location;
import com.doctour.doctourbe.repository.LocationRepository;
import jakarta.transaction.Transactional;
import org.locationtech.jts.geom.Point;
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
    public Location createLocation(String name, String description, Point coordinates, String city, String address, String postalCode) {
        Location location = new Location();
        location.setName(name);
        location.setDescription(description);
        location.setCoordinates(coordinates);
        location.setPostalCode(postalCode);
        location.setCity(city);
        location.setAddress(address);
        locationRepository.save(location);
        return location;
    }

    public Optional<Location> findById(Long id){
        return locationRepository.findById(id);
    }

    public List<Location> findInRadius(Point center, Double radius){
        return locationRepository.findWithinRadius(center, radius);
    }

    @Transactional
    public void save(Location location) {
        locationRepository.save(location);
    }

    @Scheduled(cron = "0 0 3 * * *")
    @Transactional
    public void deleteUnused() {
        locationRepository.deleteUnused();
    }
}
