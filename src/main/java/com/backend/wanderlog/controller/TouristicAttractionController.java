package com.backend.wanderlog.controller;

import com.backend.wanderlog.exception.ResourceNotFoundException;
import com.backend.wanderlog.exception.ValidationException;
import com.backend.wanderlog.model.TouristicAttraction;
import com.backend.wanderlog.model.TravelDestination;
import com.backend.wanderlog.repository.TouristicAttractionRepository;
import com.backend.wanderlog.repository.TravelDestinationRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/wanderlog/v1")
public class TouristicAttractionController {
    private TouristicAttractionRepository touristicAttractionRepository;
    private TravelDestinationRepository travelDestinationRepository;

    public TouristicAttractionController(TouristicAttractionRepository touristicAttractionRepository, TravelDestinationRepository travelDestinationRepository){
        this.touristicAttractionRepository = touristicAttractionRepository;
        this.travelDestinationRepository = travelDestinationRepository;
    }

    //Endopint (url): http://localhost:8080/api/wanderlog/v1/travelDestinations/1/touristicAttractions
    //Method: GET
    @Transactional(readOnly = true)
    @GetMapping("/travelDestinations/{travelDestinationId}/touristicAttractions")
    public ResponseEntity<List<TouristicAttraction>> getTouristicAttractionsByTravelDestination(@PathVariable(name = "travelDestinationId") Long travelDestinationId){
        TravelDestination travelDestination = travelDestinationRepository.findById(travelDestinationId)
                .orElseThrow(()->new ResourceNotFoundException("No se encuentra el destino de viaje con el id #"+travelDestinationId));;
        return new ResponseEntity<List<TouristicAttraction>>(touristicAttractionRepository.findTouristicAttractionByTravelDestinationId(travelDestinationId),HttpStatus.OK);
    }

    //Endopint (url): http://localhost:8080/api/wanderlog/v1/library/travelDestinations/1/touristicAttractions
    //Method: POST
    @Transactional
    @PostMapping("/travelDestinations/{travelDestinationId}/touristicAttractions")
    public ResponseEntity<TouristicAttraction> createTouristicAttractionsByTravelDestination(@PathVariable(name = "travelDestinationId")Long travelDestinationId, @RequestBody TouristicAttraction touristicAttraction){
        TravelDestination travelDestination = travelDestinationRepository.findById(travelDestinationId)
                .orElseThrow(()->new ResourceNotFoundException("No se encuentra el destino turistico con el id="+travelDestinationId));
        validateTouristicAttraction(touristicAttraction);
        //touristicAttraction.setDate(LocalDate.now());
        //touristicAttraction.setDevolutionDate(LocalDate.now().plusDays(3));
        return new ResponseEntity<TouristicAttraction>(touristicAttractionRepository.save(touristicAttraction), HttpStatus.CREATED);
    }


    private void validateTouristicAttraction(TouristicAttraction touristicAttraction){
        if(touristicAttraction.getImageUrl()==null || touristicAttraction.getImageUrl().trim().isEmpty()){
            throw new ValidationException("La URL de la imagen no puede estar vacía");
        }
        if(touristicAttraction.getName()==null || touristicAttraction.getName().trim().isEmpty()){
            throw new ValidationException("El nombre de la compañia de vuelo no puede estar vacío");
        }
        if(touristicAttraction.getDescription()==null || touristicAttraction.getDescription().trim().isEmpty()){
            throw new ValidationException("La descripción de la compañia de vuelo no puede estar vacía");
        }


        if(touristicAttraction.getName().length()>255){
            throw new ValidationException("El nombre de la compañia de vuelo no puede tener mas de 255 caracteres");
        }
        if(touristicAttraction.getImageUrl().length()>255){
            throw new ValidationException("La URL de la imagen no puede tener mas de 255 caracteres");
        }
        if(touristicAttraction.getDescription().length()>2000){
            throw new ValidationException("La descripción de la compañia de vuelo no debe tener más de 2000 caracteres");
        }
    }
}
