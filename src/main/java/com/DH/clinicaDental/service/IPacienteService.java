package com.DH.clinicaDental.service;

import com.DH.clinicaDental.exception.BadRequestException;
import com.DH.clinicaDental.exception.ResourceNotFoundException;
import com.DH.clinicaDental.model.entity.Paciente;
import java.util.Collection;
import java.util.Optional;

public interface IPacienteService {
    Paciente guardarPaciente(Paciente paciente) throws BadRequestException;
    void eliminarPaciente(Long id) throws ResourceNotFoundException;
    Optional<Paciente> buscarPaciente(Long id) throws ResourceNotFoundException;
    Collection<Paciente> buscarTodosLosPacientes();
    Paciente actualizarPaciente(Paciente paciente) throws BadRequestException, ResourceNotFoundException;
    Optional<Paciente> buscarPacientePorCorreo(String correo) throws ResourceNotFoundException;
    boolean dniYaExiste(String dni);
    boolean emailYaExiste(String email);
}
