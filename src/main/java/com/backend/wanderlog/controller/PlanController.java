package com.backend.wanderlog.controller;

import com.backend.wanderlog.exception.ResourceNotFoundException;
import com.backend.wanderlog.exception.ValidationException;
import com.backend.wanderlog.model.Hotel;
import com.backend.wanderlog.model.Plan;
import com.backend.wanderlog.model.TravelDestination;
import com.backend.wanderlog.repository.HotelRepository;
import com.backend.wanderlog.repository.PlanRepository;
import com.backend.wanderlog.repository.TravelDestinationRepository;
import com.backend.wanderlog.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/wanderlog/v1")
public class PlanController {
    private PlanRepository planRepository;
    private UserRepository userRepository;

    public PlanController(UserRepository userRepository, PlanRepository planRepository){
        this.userRepository = userRepository;
        this.planRepository = planRepository;
    }

    //Endopint (url): http://localhost:8080/api/wanderlog/v1/plans
    //Method: GET
    @Transactional(readOnly = true)
    @GetMapping("/plans")
    public ResponseEntity<List<Plan>> getPlans(){
        List<Plan> plans = planRepository.findAll();
        return new ResponseEntity<List<Plan>>(plans,HttpStatus.OK);
    }

    //Endopint (url): http://localhost:8080/api/wanderlog/v1/plans/1
    //Method: get
    @Transactional
    @GetMapping("/plans/{id}")
    public ResponseEntity<Plan> createHotelsByTravelDestination(@PathVariable(name = "id")Long id){
        Plan plan = planRepository.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("No se encuentra el plan con el id="+id));
        //hotel.setDate(LocalDate.now());
        //hotel.setDevolutionDate(LocalDate.now().plusDays(3));
        return new ResponseEntity<Plan>(plan, HttpStatus.CREATED);
    }
}
