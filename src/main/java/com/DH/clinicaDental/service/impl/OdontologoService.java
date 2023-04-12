package com.DH.clinicaDental.service.impl;

import com.DH.clinicaDental.exception.BadRequestException;
import com.DH.clinicaDental.exception.ResourceNotFoundException;
import com.DH.clinicaDental.model.entity.Odontologo;
import com.DH.clinicaDental.repository.OdontologoRepository;
import com.DH.clinicaDental.service.IOdontologoService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;

@Service
public class OdontologoService implements IOdontologoService {
    private final static Logger LOGGER = Logger.getLogger(OdontologoService.class);
    @Autowired
    private OdontologoRepository odontologoRepository;
    private boolean esNulo(Object objeto){
        return objeto == null;
    }
    @Override
    public Odontologo guardarOdontologo(Odontologo odontologo) throws BadRequestException {
        if (esNulo(odontologo.getId())) {
            if (!matriculaYaExiste(odontologo.getMatricula())) {
                LOGGER.info("Exito, Odontologo registrado");
                return odontologoRepository.save(odontologo);
            } else {
                LOGGER.warn("Error, MATRICULA ya registrada");
                throw new BadRequestException("MATRICULA ya registrada en la base de datos");
            }
        } else {
            LOGGER.warn("Error, ID del Odontologo debe ser nulo");
            throw new BadRequestException("El ID debe ser nulo");
        }
    }

    @Override
    public void eliminarOdontologo(Long id) throws ResourceNotFoundException {
        Optional<Odontologo> odontologoParaEliminar = buscarOdontologo(id);
        if (odontologoParaEliminar.isPresent()) {
            odontologoRepository.deleteById(id);
            LOGGER.info("Exito, Odontologo eliminado");
        } else {
            LOGGER.error("Error, Odontologo inexistente");
            throw new ResourceNotFoundException("El Odontologo no existe");
        }
    }

    @Override
    public Optional<Odontologo> buscarOdontologo(Long id) throws ResourceNotFoundException {
        Optional<Odontologo> odontologoBuscado = odontologoRepository.findById(id);
        if (odontologoBuscado.isPresent()) {
            LOGGER.info("Exito, Odontologo encontrado");
            return odontologoBuscado;
        } else {
            LOGGER.error("Error, Odontologo inexistente");
            throw new ResourceNotFoundException("El Odontologo no existe");
        }
    }

    @Override
    public Collection<Odontologo> buscarTodosLosOdontologos() {
        return odontologoRepository.findAll();
    }

    @Override
    public Odontologo actualizarOdontologo(Odontologo odontologo) throws BadRequestException, ResourceNotFoundException {
        if (!esNulo(odontologo.getId())) {
            Optional<Odontologo> odontologoParaActualizar = buscarOdontologo(odontologo.getId());
            if (odontologoParaActualizar.isPresent()) {
                LOGGER.info("Exito, Odontologo actualizado");
                return odontologoRepository.save(odontologo);
            } else {
                LOGGER.warn("Error, Odontologo inexistente");
                throw new BadRequestException("El Odontologo no existe");
            }
        } else {
            LOGGER.error("Error, ID no debe ser nulo");
            throw new BadRequestException("EL ID no debe ser nulo");
        }
    }

    @Override
    public boolean matriculaYaExiste(String matricula) {
        return odontologoRepository.existsOdontologoByMatricula(matricula);
    }
}
