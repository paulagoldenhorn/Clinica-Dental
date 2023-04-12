package com.DH.clinicaDental.service;

import com.DH.clinicaDental.exception.BadRequestException;
import com.DH.clinicaDental.exception.ResourceNotFoundException;
import com.DH.clinicaDental.model.dto.TurnoDTO;
import java.util.Collection;
import java.util.Optional;

public interface ITurnoService {
    TurnoDTO guardarTurno(TurnoDTO turno) throws ResourceNotFoundException, BadRequestException;
    void eliminarTurno(Long id) throws ResourceNotFoundException, BadRequestException;
    Optional<TurnoDTO> buscarTurno(Long id) throws ResourceNotFoundException, BadRequestException;
    Collection<TurnoDTO> buscarTodosLosTurnos();
    TurnoDTO actualizarTurno(TurnoDTO turno) throws BadRequestException, ResourceNotFoundException;
}
