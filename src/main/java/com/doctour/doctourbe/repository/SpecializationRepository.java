package com.doctour.doctourbe.repository;

import com.doctour.doctourbe.model.Specialization;
import org.jspecify.annotations.NullMarked;
import org.springframework.beans.factory.BeanRegistry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SpecializationRepository extends JpaRepository<Specialization, Long> {

    @NullMarked
    Optional<Specialization> findById(Long id);

    Optional<Specialization> findByName(String name);

    @Modifying
    @Query(value = "DELETE FROM specialization WHERE id NOT IN (SELECT specialization_id FROM app_user WHERE specizalization_id IS NOT NULL)", nativeQuery = true)
    void deleteUnused();
}
