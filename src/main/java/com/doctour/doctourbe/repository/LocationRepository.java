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
    @Query(value = "DELETE FROM location WHERE id NOT IN (SELECT location_id FROM app_user WHERE location_id IS NOT NULL)", nativeQuery = true)
    void deleteUnused();
}
