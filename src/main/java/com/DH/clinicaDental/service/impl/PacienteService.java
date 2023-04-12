package com.DH.clinicaDental.service.impl;

import com.DH.clinicaDental.exception.BadRequestException;
import com.DH.clinicaDental.exception.ResourceNotFoundException;
import com.DH.clinicaDental.model.entity.Paciente;
import com.DH.clinicaDental.repository.PacienteRepository;
import com.DH.clinicaDental.service.IPacienteService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;
@Service
public class PacienteService implements IPacienteService {
    private final static Logger LOGGER = Logger.getLogger(PacienteService.class);
    @Autowired
    PacienteRepository pacienteRepository;
    private boolean esNulo(Object objeto){
        return objeto == null;
    }
    @Override
    public Paciente guardarPaciente(Paciente paciente) throws BadRequestException {
        if (esNulo(paciente.getId())) {
            if (!dniYaExiste(paciente.getDni())) {
                if (!emailYaExiste(paciente.getEmail())) {
                    if (esNulo(paciente.getDomicilio().getId())) {
                        LOGGER.info("Exito, Paciente registrado");
                        return pacienteRepository.save(paciente);
                    } else {
                        LOGGER.warn("Error, ID del Domicilio debe ser nulo");
                        throw new BadRequestException("Domicilio ya registrado en la base de datos");
                    }
                } else {
                    LOGGER.warn("Error, EMAIL ya registrado");
                    throw new BadRequestException("EMAIL ya registrado en la base de datos");
                }
            } else {
                LOGGER.warn("Error, DNI ya registrado");
                throw new BadRequestException("DNI ya registrado en la base de datos");
            }
        } else {
            LOGGER.warn("Error, ID del Paciente debe ser nulo");
            throw new BadRequestException("El ID debe ser nulo");
        }
    }

    @Override
    public void eliminarPaciente(Long id) throws ResourceNotFoundException {
        Optional<Paciente> pacienteParaEliminar = buscarPaciente(id);
        if (pacienteParaEliminar.isPresent()) {
            Long domicilio_id = pacienteParaEliminar.get().getDomicilio().getId();
            if (!esNulo(domicilio_id)) {
                pacienteRepository.deleteById(id);
                LOGGER.info("Exito, Paciente eliminado");
            } else {
                LOGGER.error("Error, Domicilio inexistente");
                throw new ResourceNotFoundException("El Domiclio no existe");
            }
        } else {
            LOGGER.error("Error, Paciente inexistente");
            throw new ResourceNotFoundException("El Paciente no existe");
        }
    }

    @Override
    public Optional<Paciente> buscarPaciente(Long id) throws ResourceNotFoundException {
        Optional<Paciente> pacienteBuscado = pacienteRepository.findById(id);
        if (pacienteBuscado.isPresent()) {
            LOGGER.info("Exito, Paciente encontrado");
            return pacienteBuscado;
        } else {
            LOGGER.error("Error, Paciente inexistente");
            throw new ResourceNotFoundException("El Paciente no existe");
        }
    }

    @Override
    public Collection<Paciente> buscarTodosLosPacientes() {
        return pacienteRepository.findAll();
    }

    @Override
    public Paciente actualizarPaciente(Paciente paciente) throws BadRequestException, ResourceNotFoundException {
        if (!esNulo(paciente.getId())) {
            Optional<Paciente> pacienteParaActualizar = buscarPaciente(paciente.getId());
            if (pacienteParaActualizar.isPresent()) {
                if (!esNulo(paciente.getDomicilio().getId())) {
                    LOGGER.info("Exito, Paciente actualizado");
                    return pacienteRepository.save(paciente);
                } else {
                    LOGGER.warn("Error, ID del Domicilio no debe ser nulo");
                    throw new BadRequestException("El Domicilio no existe");
                }
            } else {
                LOGGER.warn("Error, Paciente inexistente");
                throw new BadRequestException("El Paciente no existe");
            }
        } else {
            LOGGER.error("Error, ID no debe ser nulo");
            throw new BadRequestException("El ID no debe ser nulo");
        }
    }

    @Override
    public Optional<Paciente> buscarPacientePorCorreo(String correo) throws ResourceNotFoundException {
        Optional<Paciente> pacienteBuscado = pacienteRepository.findByEmail(correo);
        if (pacienteBuscado.isPresent()) {
            LOGGER.info("Exito, Paciente encontrado");
            return pacienteBuscado;
        } else {
            LOGGER.error("Error, Paciente inexistente");
            throw new ResourceNotFoundException("Paciente con EMAIL " + correo + " no existe");
        }
    }
    @Override
    public boolean dniYaExiste(String dni) {
        return pacienteRepository.existsPacienteByDni(dni);
    }

    @Override
    public boolean emailYaExiste(String email) {
        return pacienteRepository.existsPacienteByEmail(email);
    }
}
