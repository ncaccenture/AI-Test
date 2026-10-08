package com.example.dex_gui.service;

import com.example.dex_gui.entity.Quartet;
import com.example.dex_gui.repository.QuartetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.List;

@Service
public class QuartetService {

    private final QuartetRepository quartetRepository;
    private final FolderService folderService;

    public QuartetService(QuartetRepository quartetRepository, FolderService folderService){

        this.quartetRepository = quartetRepository;
        this.folderService = folderService;
    }

    public Quartet createQuartet(Quartet quartet) throws IOException {
        boolean existingQuartet = searchQuartet(quartet);

        if(existingQuartet){
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Quartet already exists."
            );
        }

        Quartet savedQuartet = quartetRepository.save(quartet);

        folderService.createQuartetFolders(
                quartet.getCompound(),
                quartet.getStudy(),
                quartet.getDepartment(),
                quartet.getPartner()
        );
        return savedQuartet;
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

