package com.doctour.doctourbe.service;

import com.doctour.doctourbe.exception.GenderException;
import com.doctour.doctourbe.model.Gender;
import com.doctour.doctourbe.repository.GenderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class GenderService {

    @Autowired
    private GenderRepository genderRepository;

    public List<Gender> findAll() {
        return genderRepository.findAll();
    }

    public Optional<Gender> findById(Long id){
        return genderRepository.findById(id);
    }

    public Optional<Gender> findByName(String name) { return genderRepository.findByName(name);}

    public Optional<Gender> findByShortname(String shortname) { return genderRepository.findByShortname(shortname);}

    @Transactional
    public Gender createGender(String name, String shortname) {
        if(findByName(name).isPresent()){
            throw new GenderException("NAME_TAKEN");
        }
        if(findByShortname(shortname).isPresent()){
            throw new GenderException("SHORTNAME_TAKEN");
        }
        Gender gender = new Gender();
        gender.setName(name);
        gender.setShortname(shortname);
        this.save(gender);
        return gender;
    }

    @Transactional
    public void save(Gender gender) {
        genderRepository.save(gender);
    }
}
