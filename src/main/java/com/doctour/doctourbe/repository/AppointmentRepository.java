package com.doctour.doctourbe.repository;

import com.doctour.doctourbe.model.AppUser;
import com.doctour.doctourbe.model.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, UUID> {

    public Optional<Appointment> findAppointmentByUuid(UUID uuid);

    public List<Appointment> findAppointmentsByCustomer(AppUser customer);

    public List<Appointment> findAppointmentsByDoctor(AppUser doctor);

    public List<Appointment> findAppointmentsByDoctorAndDate(AppUser doctor, LocalDate date);

    public List<Appointment> findByDate(LocalDate date);
}
