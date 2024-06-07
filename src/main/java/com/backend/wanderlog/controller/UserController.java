package com.backend.wanderlog.controller;

import com.backend.wanderlog.exception.ValidationException;
import com.backend.wanderlog.model.User;
import com.backend.wanderlog.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/wanderlog/v1")
public class UserController {
    private UserRepository userRepository;
    public UserController(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    //Endopint (url): http://localhost:8080/api/wanderlog/v1/login
    //Method: GET

    @Transactional(readOnly = true)
    @GetMapping("/login")
    public ResponseEntity<User> login(@RequestParam(value = "user") String user, @RequestParam(value = "password") String password){
        return new ResponseEntity<User>(userRepository.getUserByEmailAndPassword(user, password), HttpStatus.OK);
    }

    @Transactional
    @GetMapping("/login/{user}/{password}")
    public ResponseEntity<User> loginAlt(@PathVariable(value = "user") String user, @RequestParam(value = "password") String password){
        return new ResponseEntity<User>(userRepository.getUserByEmailAndPassword(user, password), HttpStatus.OK);
    }

    //Endopint (url): http://localhost:8080/api/wanderlog/v1/register
    //Method: POST
    @Transactional
    @PostMapping("/register")
    public ResponseEntity<User> createAccount(@RequestBody User user){
        existsAccountByUsernameAndPassword(user);
        validateAccount(user);
        return new ResponseEntity<User>(userRepository.save(user), HttpStatus.CREATED);
    }

    private void validateAccount(User user){
        if(user.getFullName()==null || user.getFullName().trim().isEmpty()){
            throw new ValidationException("El nombre del cliente debe ser obligatorio");
        }

        if(user.getPhone()==null || user.getPhone().trim().isEmpty()){
            throw new ValidationException("El número de telefono debe ser obligatorio");
        }

        if(user.getFullName().length()>255){
            throw new ValidationException("El nombre de cliente no debe exceder los 255 caracteres");
        }

        if(user.getEmail().length()>255){
            throw new ValidationException("El email de cliente no debe exceder los 255 caracteres");
        }

        if(user.getPassword().length()>255){
            throw new ValidationException("La contraseña de cliente no debe exceder los 255 caracteres");
        }

        if(user.getPhone().length() != 9){
            throw new ValidationException("El número de telefono debe tener una longitud de 9 caracteres");
        }


    }

    private void existsAccountByUsernameAndPassword(User user){
        if(userRepository.existsByEmailAndPassword(user.getEmail(), user.getPassword())){
            throw new ValidationException("No se puede registrar la cuenta porque ya existe una con ese usuario y esa contraseña");
        }
    }
}
