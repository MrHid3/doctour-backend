package com.doctour.doctourbe.repository;

import com.doctour.doctourbe.model.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LocationRepository extends JpaRepository<Location, Integer> {

    Optional<Location> findById(long id);

    @Modifying
    @Query("DELETE FROM location l LEFT JOIN app_user a ON a.location_id = l.id WHERE a.uuid IS NULL")
    void deleteUnused();
}
