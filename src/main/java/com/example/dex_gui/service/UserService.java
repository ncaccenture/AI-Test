package com.example.dex_gui.service;

import com.example.dex_gui.entity.Quartet;
import com.example.dex_gui.entity.User;
import com.example.dex_gui.repository.QuartetRepository;
import com.example.dex_gui.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private QuartetRepository quartetRepository;

    public User createUser ( User user){
        Optional<User> findUser = userRepository.findByEmail(user.getEmail());

        if(findUser.isPresent()){
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "User is already exist with email : " + user.getEmail()
            );
        }

        return userRepository.save(user);
    }
    public User getUser(Long id){
        return userRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("User not found with id : "+ id));
    }

    public List<User> getAllUsers (){
        return userRepository.findAll();
    }

    public User getUserByEmail(String email){
        return userRepository.findByEmail(email)
                .orElseThrow(()-> new RuntimeException("User not found"));
    }

    public void grantQuartetAccess(
            Long userId,
            String compound,
            String study,
            String department,
            String partner) {

        User user = userRepository.findById(userId)
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        Quartet quartet = quartetRepository
                .findByCompoundAndStudyAndDepartmentAndPartner(
                        compound,
                        study,
                        department,
                        partner
                )
                .orElseThrow(() ->
                        new RuntimeException("Quartet not found"));

        user.getQuartets().add(quartet);

        userRepository.save(user);
    }

    public List<Quartet> getUserQuartets(Long userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() ->
                        new RuntimeException("User not found with id: " + userId));

        return new ArrayList<>(user.getQuartets());
    }
}
