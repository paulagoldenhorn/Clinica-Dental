package com.DH.clinicaDental.controller;

import com.DH.clinicaDental.exception.BadRequestException;
import com.DH.clinicaDental.exception.ResourceNotFoundException;
import com.DH.clinicaDental.model.dto.TurnoDTO;
import com.DH.clinicaDental.service.IOdontologoService;
import com.DH.clinicaDental.service.IPacienteService;
import com.DH.clinicaDental.service.ITurnoService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/turnos")
public class TurnoController {
    private final static Logger LOGGER = Logger.getLogger(TurnoController.class);
    @Autowired
    private ITurnoService turnoService;
    @Autowired
    private IPacienteService pacienteService;
    @Autowired
    private IOdontologoService odontologoService;

    @PostMapping
    public ResponseEntity<?> guardarTurno(@RequestBody TurnoDTO turno) throws BadRequestException, ResourceNotFoundException {
        return ResponseEntity.status(HttpStatus.CREATED).body(turnoService.guardarTurno(turno));
    }
    @PutMapping
    public ResponseEntity<?> actualizarTurno(@RequestBody TurnoDTO turno) throws BadRequestException, ResourceNotFoundException {
        return ResponseEntity.status(HttpStatus.OK).body(turnoService.actualizarTurno(turno));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarTurno(@PathVariable Long id) throws ResourceNotFoundException, BadRequestException {
        turnoService.eliminarTurno(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> buscarTurno(@PathVariable Long id) throws ResourceNotFoundException, BadRequestException {
        return ResponseEntity.status(HttpStatus.OK).body(turnoService.buscarTurno(id));
    }
    @GetMapping
    public ResponseEntity<?> buscarTodosLosTurnos(){
        Collection<TurnoDTO> turnos = turnoService.buscarTodosLosTurnos();
        if (turnos.size() == 0) return ResponseEntity.status(HttpStatus.NO_CONTENT).body(turnos);
        else return ResponseEntity.status(HttpStatus.OK).body(turnos);
    }
}
