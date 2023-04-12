package com.DH.clinicaDental.service.impl;

import com.DH.clinicaDental.exception.BadRequestException;
import com.DH.clinicaDental.exception.ResourceNotFoundException;
import com.DH.clinicaDental.model.entity.Odontologo;
import com.DH.clinicaDental.service.IOdontologoService;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Collection;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest
class OdontologoServiceTest {
    @Autowired
    private IOdontologoService odontologoService;

    @Test
    @Order(1)
    public void guardarOdontologo() throws BadRequestException {
        Odontologo odontologoParaGuardar = new Odontologo("ABC123", "Pereira", "Guillermo");

        Odontologo odontologoGuardado = odontologoService.guardarOdontologo(odontologoParaGuardar);
        assertEquals(odontologoGuardado.getMatricula(),"ABC123");
    }

    @Test
    @Order(2)
    public void actualizarOdontologo() throws BadRequestException, ResourceNotFoundException {
        Optional<Odontologo> odontologoParaActualizar = odontologoService.buscarOdontologo(1L);
        odontologoParaActualizar.get().setNombre("Walter");
        odontologoParaActualizar.get().setApellido("Benegas");

        Odontologo odontologoActualizado = odontologoService.actualizarOdontologo(odontologoParaActualizar.get());

        assertEquals("Walter", odontologoActualizado.getNombre());
        assertEquals("Benegas", odontologoActualizado.getApellido());
    }

    @Test
    @Order(3)
    public void buscarOdontologoPorId() throws ResourceNotFoundException {
        Optional<Odontologo> odontologoEncontrado = odontologoService.buscarOdontologo(1L);

        assertEquals("Walter", odontologoEncontrado.get().getNombre());
    }

    @Test
    @Order(4)
    public void buscarOdontologos() {
        Collection<Odontologo> odontologosGuardados = odontologoService.buscarTodosLosOdontologos();

        assertTrue(1 == odontologosGuardados.size());
    }

    @Test
    @Order(5)
    public void eliminarOdontologo() throws ResourceNotFoundException {
        odontologoService.eliminarOdontologo(1L);
        Collection<Odontologo> odontologos = odontologoService.buscarTodosLosOdontologos();
        assertTrue(odontologos.size() == 0);
    }
}