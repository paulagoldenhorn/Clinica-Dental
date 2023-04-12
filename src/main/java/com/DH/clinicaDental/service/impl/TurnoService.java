package com.DH.clinicaDental.service.impl;

import com.DH.clinicaDental.exception.BadRequestException;
import com.DH.clinicaDental.exception.ResourceNotFoundException;
import com.DH.clinicaDental.model.dto.TurnoDTO;
import com.DH.clinicaDental.model.entity.Turno;
import com.DH.clinicaDental.repository.TurnoRepository;
import com.DH.clinicaDental.service.IOdontologoService;
import com.DH.clinicaDental.service.IPacienteService;
import com.DH.clinicaDental.service.ITurnoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class TurnoService implements ITurnoService {
    private final static Logger LOGGER = Logger.getLogger(TurnoService.class);
    @Autowired
    private TurnoRepository turnoRepository;
    @Autowired
    private IOdontologoService odontologoService;
    @Autowired
    private IPacienteService pacienteService;
    @Autowired
    private ObjectMapper mapper;

    private boolean esNulo(Object objeto){
        return objeto == null;
    }
    private boolean validarPacienteYOdontologo(Long paciente_id, Long odonrologo_id) throws ResourceNotFoundException {
        return pacienteService.buscarPaciente(paciente_id).isPresent() &&
                odontologoService.buscarOdontologo(odonrologo_id).isPresent();
    }
     @Override
    public TurnoDTO guardarTurno(TurnoDTO turno) throws ResourceNotFoundException, BadRequestException {
        if (esNulo(turno.getId())) {
            if (validarPacienteYOdontologo(turno.getPaciente().getId(), turno.getOdontologo().getId())) {
                Turno turnoParaGuardar = mapper.convertValue(turno, Turno.class);
                Turno turnoGuardado = turnoRepository.save(turnoParaGuardar);
                LOGGER.info("Exito, Turno registrado");

                TurnoDTO turnoDTO = mapper.convertValue(turnoGuardado, TurnoDTO.class);
                return turnoDTO;
            } else {
                LOGGER.warn("Error, el Paciente u Odonotlogo no existen");
                throw new BadRequestException("Paciente u Odontologo inexistente");
            }
        } else {
            LOGGER.warn("Error, ID del Turno debe ser nulo");
            throw new BadRequestException("Turno ya registrado en la base de datos");
        }
    }

    @Override
    public void eliminarTurno(Long id) throws ResourceNotFoundException, BadRequestException {
        Optional<TurnoDTO> turnoParaEliminar = buscarTurno(id);
        if (turnoParaEliminar.isPresent()) {
            turnoRepository.deleteById(id);
            LOGGER.info("Exito, Turno eliminado");
        }
        else {
            LOGGER.error("Error, Turno inexistente");
            throw new ResourceNotFoundException("El Turno no existe");
        }
    }

    @Override
    public Optional<TurnoDTO> buscarTurno(Long id) throws ResourceNotFoundException, BadRequestException {
        if (!esNulo(id)) {
            Optional<Turno> turnoBuscado = turnoRepository.findById(id);
            if (turnoBuscado.isPresent()) {
                TurnoDTO turnoDTO = mapper.convertValue(turnoBuscado.get(), TurnoDTO.class);
                LOGGER.info("Exito, Turno encontrado");
                return Optional.of(turnoDTO);
            }
            else {
                LOGGER.error("Error, Turno inexistente");
                throw new ResourceNotFoundException("El Turno no existe");
            }
        } else {
            LOGGER.error("Error, Turno inexistente");
            throw new BadRequestException("El ID del Turno a buscar no puede ser nulo");
        }
    }

    @Override
    public Collection<TurnoDTO> buscarTodosLosTurnos() {
        List<Turno> turnos =  turnoRepository.findAll();
        Set<TurnoDTO> turnosDTO = new HashSet<>();
        for (Turno t : turnos) {
            TurnoDTO turnoDTO = mapper.convertValue(t, TurnoDTO.class);
            turnosDTO.add(turnoDTO);
        }
        LOGGER.info("Exito, Turnos encontrados");
        return turnosDTO;
    }

    @Override
    public TurnoDTO actualizarTurno(TurnoDTO turno) throws ResourceNotFoundException, BadRequestException {
        if (!esNulo(turno.getId())) {
            if (buscarTurno(turno.getId()).isPresent()) {
                if (validarPacienteYOdontologo(turno.getPaciente().getId(), turno.getOdontologo().getId())) {
                    Turno turnoParaActualizar = mapper.convertValue(turno, Turno.class);
                    Turno turnoActualizado = turnoRepository.save(turnoParaActualizar);
                    LOGGER.info("Exito, Turno actualizado");

                    TurnoDTO turnoDTO = mapper.convertValue(turnoActualizado, TurnoDTO.class);
                    return turnoDTO;
                } else {
                    LOGGER.warn("Error, el Paciente u Odonotlogo no existen");
                    throw new BadRequestException("Paciente u Odontologo inexistente");
                }
            } else {
                LOGGER.warn("Error, Turno inexistente");
                throw new BadRequestException("El Turno no existe");
            }
        } else {
            LOGGER.warn("Error, ID del Turno no debe ser nulo");
            throw new BadRequestException("El ID del Turno a actualizar no puede ser nulo");
        }

    }
}
