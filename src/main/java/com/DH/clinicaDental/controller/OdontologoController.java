package com.DH.clinicaDental.controller;

import com.DH.clinicaDental.exception.BadRequestException;
import com.DH.clinicaDental.exception.ResourceNotFoundException;
import com.DH.clinicaDental.model.entity.Odontologo;
import com.DH.clinicaDental.service.IOdontologoService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/odontologos")
public class OdontologoController {
    private final static Logger LOGGER = Logger.getLogger(OdontologoController.class);
    @Autowired
    private IOdontologoService odontologoService;

    @PostMapping
    public ResponseEntity<?> guardarOdontologo(@RequestBody Odontologo odontologo) throws BadRequestException{
        return ResponseEntity.status(HttpStatus.CREATED).body(odontologoService.guardarOdontologo(odontologo));
    }
    @PutMapping
    public ResponseEntity<?> actualizarOdontologo(@RequestBody Odontologo odontologo) throws BadRequestException, ResourceNotFoundException {
        return ResponseEntity.status(HttpStatus.OK).body(odontologoService.actualizarOdontologo(odontologo));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarOdontologo(@PathVariable Long id) throws ResourceNotFoundException {
        odontologoService.eliminarOdontologo(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
    @GetMapping("/{id}")
    public ResponseEntity<Object> buscarOdontologo(@PathVariable Long id) throws ResourceNotFoundException{
        return ResponseEntity.status(HttpStatus.OK).body(odontologoService.buscarOdontologo(id));
    }
    @GetMapping
    public ResponseEntity<Object> buscarTodosLosOdontologos(){
        Collection<Odontologo> odontologos = odontologoService.buscarTodosLosOdontologos();
        if (odontologos.size() == 0) return ResponseEntity.status(HttpStatus.NO_CONTENT).body(odontologos);
        else return ResponseEntity.status(HttpStatus.OK).body(odontologos);
    }
}
