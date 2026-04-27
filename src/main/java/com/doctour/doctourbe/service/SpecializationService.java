package com.doctour.doctourbe.service;

import com.doctour.doctourbe.exception.SpecializationException;
import com.doctour.doctourbe.model.Specialization;
import com.doctour.doctourbe.repository.SpecializationRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SpecializationService {

    @Autowired
    private SpecializationRepository specializationRepository;

    public List<Specialization> findAll(){
        return specializationRepository.findAll();
    }

    public Optional<Specialization> findById(Long id){
        return specializationRepository.findById(id);
    }

    public Optional<Specialization> findByName(String name){
        return specializationRepository.findByName(name);
    }

    @Transactional
    public Specialization create(String name, String description){
        if(this.findByName(name).isPresent()){
            throw new SpecializationException("EXISTS");
        }
        Specialization spec = new Specialization();
        spec.setName(name);
        spec.setDescription(description);
        return this.save(spec);
    }

    @Transactional
    public Specialization save(Specialization spec){
        return specializationRepository.save(spec);
    }

    @Scheduled(cron = "0 0 3 * * *")
    @Transactional
    public void deleteUnused(){
        specializationRepository.deleteUnused();
    }
}
