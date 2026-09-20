package com.example.dex_gui.service;

import com.example.dex_gui.entity.Quartet;
import com.example.dex_gui.repository.QuartetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class QuartetService {

    private final QuartetRepository quartetRepository;

    public QuartetService(QuartetRepository quartetRepository){
        this.quartetRepository = quartetRepository;
    }

    public Quartet createQuartet(Quartet quartet){
        boolean existingQuartet = searchQuartet(quartet);

        if(existingQuartet){
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Quartet already exists."
            );
        }
        return quartetRepository.save(quartet);
    }

    public boolean searchQuartet(Quartet quartet){

        return quartetRepository.existsByCompoundAndStudyAndDepartmentAndPartner(
                quartet.getCompound(),
                quartet.getStudy(),
                quartet.getDepartment(),
                quartet.getPartner()
        );
    }

    public List<Quartet> findAllQuartets(){
        return quartetRepository.findAll();
    }

}

