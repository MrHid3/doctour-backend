package com.doctour.doctourbe.repository;

import com.doctour.doctourbe.model.Gender;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GenderRepository extends JpaRepository<Gender, Integer> {

    public Optional<Gender> findById(Long id);
}
