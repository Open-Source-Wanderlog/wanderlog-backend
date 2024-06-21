package com.backend.wanderlog.controller;

import com.backend.wanderlog.exception.ResourceNotFoundException;
import com.backend.wanderlog.exception.ValidationException;
import com.backend.wanderlog.model.FlyCompany;
import com.backend.wanderlog.model.TravelDestination;
import com.backend.wanderlog.repository.FlyCompanyRepository;
import com.backend.wanderlog.repository.TravelDestinationRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/wanderlog/v1")
public class FlyCompanyController {
    private FlyCompanyRepository flyCompanyRepository;
    private TravelDestinationRepository travelDestinationRepository;

    public FlyCompanyController(FlyCompanyRepository flyCompanyRepository, TravelDestinationRepository travelDestinationRepository){
        this.flyCompanyRepository = flyCompanyRepository;
        this.travelDestinationRepository = travelDestinationRepository;
    }

    //Endopint (url): http://localhost:8080/api/wanderlog/v1/travelDestinations/1/flyCompanies
    //Method: GET
    @Transactional(readOnly = true)
    @GetMapping("/travelDestinations/{travelDestinationId}/flyCompanies")
    public ResponseEntity<List<FlyCompany>> getFlyCompaniesByTravelDestination(@PathVariable(name = "travelDestinationId") Long travelDestinationId){
        TravelDestination travelDestination = travelDestinationRepository.findById(travelDestinationId)
                .orElseThrow(()->new ResourceNotFoundException("No se encuentra el destino de viaje con el id #"+travelDestinationId));;
        return new ResponseEntity<List<FlyCompany>>(flyCompanyRepository.findFlyCompaniesByTravelDestinationId(travelDestinationId),HttpStatus.OK);
    }

    //Endopint (url): http://localhost:8080/api/wanderlog/v1/library/travelDestinations/1/flyCompanys
    //Method: POST
    @Transactional
    @PostMapping("/travelDestinations/{travelDestinationId}/flyCompanies")
    public ResponseEntity<FlyCompany> createFlyCompaniesByTravelDestination(@PathVariable(name = "travelDestinationId")Long travelDestinationId, @RequestBody FlyCompany flyCompany){
        TravelDestination travelDestination = travelDestinationRepository.findById(travelDestinationId)
                .orElseThrow(()->new ResourceNotFoundException("No se encuentra el destino turistico con el id="+travelDestinationId));
        validateFlyCompany(flyCompany);
        flyCompany.setTravelDestination(travelDestination); 
        return new ResponseEntity<FlyCompany>(flyCompanyRepository.save(flyCompany), HttpStatus.CREATED);
    }


    private void validateFlyCompany(FlyCompany flyCompany){
        if(flyCompany.getImageUrl()==null || flyCompany.getImageUrl().trim().isEmpty()){
            throw new ValidationException("La URL de la imagen no puede estar vacía");
        }
        if(flyCompany.getName()==null || flyCompany.getName().trim().isEmpty()){
            throw new ValidationException("El nombre de la compañia de vuelo no puede estar vacío");
        }
        if(flyCompany.getDescription()==null || flyCompany.getDescription().trim().isEmpty()){
            throw new ValidationException("La descripción de la compañia de vuelo no puede estar vacía");
        }


        if(flyCompany.getName().length()>255){
            throw new ValidationException("El nombre de la compañia de vuelo no puede tener mas de 255 caracteres");
        }
        if(flyCompany.getImageUrl().length()>255){
            throw new ValidationException("La URL de la imagen no puede tener mas de 255 caracteres");
        }
        if(flyCompany.getDescription().length()>2000){
            throw new ValidationException("La descripción de la compañia de vuelo no debe tener más de 2000 caracteres");
        }
    }
}
