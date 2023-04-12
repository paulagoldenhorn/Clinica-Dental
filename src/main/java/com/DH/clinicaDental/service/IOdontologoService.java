package com.DH.clinicaDental.service;

import com.DH.clinicaDental.exception.BadRequestException;
import com.DH.clinicaDental.exception.ResourceNotFoundException;
import com.DH.clinicaDental.model.entity.Odontologo;

import java.util.Collection;
import java.util.Optional;

public interface IOdontologoService {
    Odontologo guardarOdontologo(Odontologo odontologo) throws BadRequestException;
    void eliminarOdontologo(Long id) throws ResourceNotFoundException;
    Optional<Odontologo> buscarOdontologo(Long id) throws ResourceNotFoundException;
    Collection<Odontologo> buscarTodosLosOdontologos();
    Odontologo actualizarOdontologo(Odontologo odontologo) throws BadRequestException, ResourceNotFoundException;
    boolean matriculaYaExiste(String matricula);
}
