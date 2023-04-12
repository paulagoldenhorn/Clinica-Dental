package com.DH.clinicaDental.service.impl;

import com.DH.clinicaDental.exception.BadRequestException;
import com.DH.clinicaDental.exception.ResourceNotFoundException;
import com.DH.clinicaDental.model.entity.Domicilio;
import com.DH.clinicaDental.model.entity.Paciente;
import com.DH.clinicaDental.service.IPacienteService;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest
class PacienteServiceTest {
    @Autowired
    private IPacienteService pacienteService;

    @Test
    @Order(1)
    public void guardarPaciente() throws BadRequestException {
        Paciente pacienteParaGuardar = new Paciente("Stura","Sofia","45678945",LocalDate.parse("2023-03-29"),"sturita4@gmail.com",
                new Domicilio("Austria","4567","CABA","Buenos Aires"));

        Paciente pacienteGuardado = pacienteService.guardarPaciente(pacienteParaGuardar);
        assertTrue(pacienteGuardado.getDni() == "45678945");
    }

    @Test
    @Order(2)
    public void actualizarPaciente() throws ResourceNotFoundException, BadRequestException {
        Optional<Paciente> pacienteParaActualizar = pacienteService.buscarPaciente(1L);
        pacienteParaActualizar.get().setNombre("Belen");
        pacienteParaActualizar.get().getDomicilio().setCalle("Virrey Feliu");

        Paciente pacienteActualizado = pacienteService.actualizarPaciente(pacienteParaActualizar.get());

        assertEquals("Belen", pacienteActualizado.getNombre());
        assertEquals("Virrey Feliu", pacienteActualizado.getDomicilio().getCalle());
    }

    @Test
    @Order(3)
    public void buscarPacientePorId() throws ResourceNotFoundException {
       Optional<Paciente> pacienteEncontrado = pacienteService.buscarPaciente(1L);

       assertNotNull(pacienteEncontrado);
       assertEquals("Belen", pacienteEncontrado.get().getNombre());
    }

    @Test
    @Order(4)
    public void buscarPacientePorCorreo() throws ResourceNotFoundException {
        Optional<Paciente> pacienteEncontrado = pacienteService.buscarPacientePorCorreo("sturita4@gmail.com");

        assertEquals(1L, pacienteEncontrado.get().getId());
    }

    @Test
    @Order(5)
    public void buscarPacientes() {
        Collection<Paciente> pacientesGuardados = pacienteService.buscarTodosLosPacientes();

        assertTrue(1 == pacientesGuardados.size());
    }

    @Test
    @Order(6)
    public void eliminarPaciente() throws ResourceNotFoundException {
        pacienteService.eliminarPaciente(1L);
        Collection<Paciente> pacientes = pacienteService.buscarTodosLosPacientes();
        assertTrue(pacientes.size() == 0);
    }
}