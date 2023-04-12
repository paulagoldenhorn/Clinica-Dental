package com.DH.clinicaDental.controller;

import com.DH.clinicaDental.exception.BadRequestException;
import com.DH.clinicaDental.exception.ResourceNotFoundException;
import com.DH.clinicaDental.model.entity.Paciente;
import com.DH.clinicaDental.service.IPacienteService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Optional;

@RestController
@RequestMapping("/pacientes")
public class PacienteController {
    private final static Logger LOGGER = Logger.getLogger(PacienteController.class);
    @Autowired
    private IPacienteService pacienteService;

    @PostMapping
    public ResponseEntity<?> guardarPaciente(@RequestBody Paciente paciente) throws BadRequestException {
        return ResponseEntity.status(HttpStatus.CREATED).body(pacienteService.guardarPaciente(paciente));
    }
    @PutMapping
    public ResponseEntity<?> actualizarPaciente(@RequestBody Paciente paciente) throws BadRequestException, ResourceNotFoundException {
        return ResponseEntity.status(HttpStatus.OK).body(pacienteService.actualizarPaciente(paciente));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarPaciente(@PathVariable Long id) throws ResourceNotFoundException{
        pacienteService.eliminarPaciente(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPaciente(@PathVariable Long id) throws ResourceNotFoundException {
        Optional<Paciente> pacienteBuscado = pacienteService.buscarPaciente(id);
        return ResponseEntity.status(HttpStatus.OK).body(pacienteBuscado);
    }
    @GetMapping("/correo/{correo}")
    public ResponseEntity<?> buscarPacientePorCorreo(@PathVariable String correo) throws ResourceNotFoundException {
        return ResponseEntity.status(HttpStatus.OK).body(pacienteService.buscarPacientePorCorreo(correo));
    }
    @GetMapping
    public ResponseEntity<?> buscarTodosLosPacientes() {
        Collection<Paciente> pacientes = pacienteService.buscarTodosLosPacientes();
        if (pacientes.size() == 0) return ResponseEntity.status(HttpStatus.NO_CONTENT).body(pacientes);
        else return ResponseEntity.status(HttpStatus.OK).body(pacientes);
    }
}
