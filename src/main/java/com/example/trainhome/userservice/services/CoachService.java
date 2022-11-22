package com.example.trainhome.userservice.services;

import com.example.trainhome.userservice.repositories.CoachRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CoachService {

    @Autowired
    private CoachRepository coachRepository;

}
