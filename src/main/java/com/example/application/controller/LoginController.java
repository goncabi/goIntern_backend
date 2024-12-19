package com.example.application.controller;

import com.example.application.models.LoginAnfrage;
import com.example.application.services.LoginService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("/api/auth")
public class LoginController {

    private final LoginService loginService;

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginAnfrage loginAnfrage) {
        try{
            if(loginService.login(loginAnfrage)){
                return new ResponseEntity<>("Login OK", HttpStatus.OK);
            }
            else{
                return new ResponseEntity<>("Login Failed", HttpStatus.BAD_REQUEST);
            }
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Ein unerwarteter Fehler ist aufgetreten.");
        }
    }
}
