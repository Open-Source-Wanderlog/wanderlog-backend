package com.backend.wanderlog.controller;


import com.backend.wanderlog.exception.ResourceNotFoundException;
import com.backend.wanderlog.model.*;
import com.backend.wanderlog.repository.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/wanderlog/v1")
public class TravelPlansController {
    private UserRepository userRepository;
    private HotelRepository hotelRepository;
    private TouristicAttractionRepository touristicAttractionRepository;
    private FlyCompanyRepository flyCompanyRepository;
    private TravelPlansRepository travelPlansRepository;

    public TravelPlansController(UserRepository userRepository, TravelPlansRepository travelPlansRepository, HotelRepository hotelRepository, TouristicAttractionRepository touristicAttractionRepository, FlyCompanyRepository flyCompanyRepository){
        this.userRepository = userRepository;
        this.travelPlansRepository = travelPlansRepository;
        this.flyCompanyRepository = flyCompanyRepository;
        this.hotelRepository = hotelRepository;
        this.touristicAttractionRepository = touristicAttractionRepository;
    }

    //Endopint (url): http://localhost:8080/api/wanderlog/v1/travel_plans_per_user/filterByNameCustomer
    //Method: GET
    @Transactional(readOnly = true)
    @GetMapping("/travelPlansPerUser/{user_id}")
    public ResponseEntity<List<TravelPlans>> getAllTravelPlansByNameCustomer(@PathVariable(name = "user_id")Long userId){
        return new ResponseEntity<List<TravelPlans>>(travelPlansRepository.findByUserId(userId),HttpStatus.OK);
    }

    //Endopint (url): http://localhost:8080/api/wanderlog/v1/travel_plans_per_user/

    //Method: POST
    @Transactional
    @PostMapping("/travel_plans_per_user/")
    public ResponseEntity<TravelPlans> createTravelPlan(@RequestBody TravelPlans travelPlans){
        User user = userRepository.findById(travelPlans.getUser().getId())
                .orElseThrow(()->new ResourceNotFoundException("No se encuentra la cuenta con el id #"+travelPlans.getUser().getId()));

        Hotel hotel = hotelRepository.findById(travelPlans.getHotel().getId())
                .orElseThrow(()->new ResourceNotFoundException("No se encuentra el hotel con el id #"+travelPlans.getHotel().getId()));

        FlyCompany flyCompany = flyCompanyRepository.findById(travelPlans.getFlyCompany().getId())
                .orElseThrow(()->new ResourceNotFoundException("No se encuentra la compañia area con el id #"+travelPlans.getHotel().getId()));

        for (TouristicAttraction temp : travelPlans.getTouristicAttractions()){
            TouristicAttraction attraction = touristicAttractionRepository.findById(temp.getId())
                .orElseThrow(()->new ResourceNotFoundException("No se encuentra la atracción turística con el id #"+temp.getId()));
        }



        //travelPlans.set(LocalDate.now());

        return new ResponseEntity<TravelPlans>(travelPlansRepository.save(travelPlans), HttpStatus.CREATED);
    }
}
