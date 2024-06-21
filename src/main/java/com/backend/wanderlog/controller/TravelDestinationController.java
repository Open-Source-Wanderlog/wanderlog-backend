package com.backend.wanderlog.controller;

import com.backend.wanderlog.exception.ResourceNotFoundException;
import com.backend.wanderlog.model.TravelDestination;
import com.backend.wanderlog.repository.TravelDestinationRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/wanderlog/v1")
public class TravelDestinationController {

    private final TravelDestinationRepository travelDestinationRepository;

    public TravelDestinationController(TravelDestinationRepository travelDestinationRepository){
        this.travelDestinationRepository = travelDestinationRepository;
    }

    //Endopint (url): http://localhost:8080/api/wanderlog/v1/travelDestinations/
    //Method: GET
    @Transactional(readOnly = true)
    @GetMapping("/travelDestinations")
    public ResponseEntity<List<TravelDestination>> getAllTravelDestinations(){
        List<TravelDestination> TravelDestinations = travelDestinationRepository.findAll();
        return new ResponseEntity<List<TravelDestination>>(TravelDestinations,HttpStatus.OK);
    }

    //Endopint (url): http://localhost:8080/api/wanderlog/v1/travelDestination?id=1
    //Method: GET
    @Transactional(readOnly = true)
    @GetMapping("/travelDestination")
    public ResponseEntity<TravelDestination> getTravelDestinationById(@RequestParam(value = "id") Long id){
        TravelDestination TravelDestination = travelDestinationRepository.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("No se encuentra el destino de viaje con el id #"+id));;
        return new ResponseEntity<TravelDestination>(TravelDestination,HttpStatus.OK);

    }


    //Endopint (url):  http://localhost:8080/api/wanderlog/v1/travelDestination/1
    //Method: GET
    @Transactional(readOnly = true)
    @GetMapping("/travelDestinations/{id}")
    public ResponseEntity<TravelDestination> getTravelDestinationByIdAlternative(@PathVariable(name = "id") Long id){
        TravelDestination TravelDestination = travelDestinationRepository.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("No se encuentra el destino de viaje con el id #"+id));;
        return new ResponseEntity<TravelDestination>(TravelDestination,HttpStatus.OK);
    }

    //Endpoint (url): http://localhost:8080/api/wanderlog/v1/travelDestinations
//Method: POST
    @Transactional
    @PostMapping("/travelDestinations")
    public ResponseEntity<TravelDestination> createTravelDestination(@RequestBody TravelDestination travelDestination){
        TravelDestination newTravelDestination = travelDestinationRepository.save(travelDestination);
        return new ResponseEntity<TravelDestination>(newTravelDestination, HttpStatus.CREATED);
    }

    //Endpoint (url): http://localhost:8080/api/wanderlog/v1/travelDestinations/{id}
//Method: DELETE
    @Transactional
    @DeleteMapping("/travelDestinations/{id}")
    public ResponseEntity<Void> deleteTravelDestination(@PathVariable Long id){
        TravelDestination travelDestination = travelDestinationRepository.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("No se encuentra el destino de viaje con el id #"+id));
        travelDestinationRepository.delete(travelDestination);
        return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
    }
}
