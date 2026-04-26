package com.doctour.doctourbe.repository;

import com.doctour.doctourbe.model.AppUser;
import com.doctour.doctourbe.model.Availability;
import com.doctour.doctourbe.model.Location;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface AvailabilityRepository extends JpaRepository<Availability, UUID> {

    Optional<Availability> findByUuid(UUID uuid);

    List<Availability> findByAppUser(AppUser doctor);

    List<Availability> findAvailabilitiesByAppUserAndDayOfWeekAndStartAfterOrEndBefore(AppUser appUser, DayOfWeek day, LocalTime start, LocalTime end);

    List<Availability> findAvailabilitiesByAppUserAndDayOfWeekAndStartBeforeAndEndAfterAndLocation(AppUser appUser, DayOfWeek day, LocalTime start, LocalTime end, Location location);
}
