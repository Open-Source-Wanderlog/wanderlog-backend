package com.backend.wanderlog.controller;

import com.backend.wanderlog.exception.ResourceNotFoundException;
import com.backend.wanderlog.exception.ValidationException;
import com.backend.wanderlog.model.Hotel;
import com.backend.wanderlog.model.TravelDestination;
import com.backend.wanderlog.repository.HotelRepository;
import com.backend.wanderlog.repository.TravelDestinationRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/wanderlog/v1")
public class HotelController {
    private HotelRepository hotelRepository;
    private TravelDestinationRepository travelDestinationRepository;

    public HotelController(HotelRepository hotelRepository, TravelDestinationRepository travelDestinationRepository){
        this.hotelRepository = hotelRepository;
        this.travelDestinationRepository = travelDestinationRepository;
    }

    //Endopint (url): http://localhost:8080/api/wanderlog/v1/travelDestinations/1/hotels
    //Method: GET
    @Transactional(readOnly = true)
    @GetMapping("/travelDestinations/{travelDestinationId}/hotels")
    public ResponseEntity<List<Hotel>> getHotelsByTravelDestination(@PathVariable(name = "travelDestinationId") Long travelDestinationId){
        TravelDestination travelDestination = travelDestinationRepository.findById(travelDestinationId)
                .orElseThrow(()->new ResourceNotFoundException("No se encuentra el destino de viaje con el id #"+travelDestinationId));;
        return new ResponseEntity<List<Hotel>>(hotelRepository.findHotelsByTravelDestinationId(travelDestinationId),HttpStatus.OK);
    }

    @Transactional
    @PostMapping("/travelDestinations/{travelDestinationId}/hotels")
    public ResponseEntity<Hotel> createHotelsByTravelDestination(@PathVariable(name = "travelDestinationId")Long travelDestinationId, @RequestBody Hotel hotel){
        TravelDestination travelDestination = travelDestinationRepository.findById(travelDestinationId)
                .orElseThrow(()->new ResourceNotFoundException("No se encuentra el destino turistico con el id="+travelDestinationId));
        validateHotel(hotel);
        hotel.setTravelDestination(travelDestination);
        return new ResponseEntity<Hotel>(hotelRepository.save(hotel), HttpStatus.CREATED);
    }


    private void validateHotel(Hotel hotel){
        if(hotel.getImage_url()==null || hotel.getImage_url().trim().isEmpty()){
            throw new ValidationException("La URL de la imagen no puede estar vacía");
        }
        if(hotel.getName()==null || hotel.getName().trim().isEmpty()){
            throw new ValidationException("El nombre del hotel no puede estar vacío");
        }
        if(hotel.getDescription()==null || hotel.getDescription().trim().isEmpty()){
            throw new ValidationException("La descripción del hotel no puede estar vacía");
        }


        if(hotel.getName().length()>255){
            throw new ValidationException("El nombre del hotel no puede tener mas de 255 caracteres");
        }
        if(hotel.getImage_url().length()>255){
            throw new ValidationException("La URL de la imagen no puede tener mas de 255 caracteres");
        }
        if(hotel.getDescription().length()>2000){
            throw new ValidationException("La descripción del hotel no debe tener más de 2000 caracteres");
        }
    }
}
