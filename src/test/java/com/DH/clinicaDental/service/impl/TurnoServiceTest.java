package com.DH.clinicaDental.service.impl;

import com.DH.clinicaDental.exception.BadRequestException;
import com.DH.clinicaDental.exception.ResourceNotFoundException;
import com.DH.clinicaDental.model.dto.TurnoDTO;
import com.DH.clinicaDental.model.entity.Domicilio;
import com.DH.clinicaDental.model.entity.Odontologo;
import com.DH.clinicaDental.model.entity.Paciente;
import com.DH.clinicaDental.service.IOdontologoService;
import com.DH.clinicaDental.service.IPacienteService;
import com.DH.clinicaDental.service.ITurnoService;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collection;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest
class TurnoServiceTest {
    @Autowired
    private ITurnoService turnoService;
    @Autowired
    private IPacienteService pacienteService;
    @Autowired
    private IOdontologoService odontologoService;

    @Test
    @Order(1)
    public void guardarTurno() throws BadRequestException, ResourceNotFoundException {
        Paciente pacienteParaGuardar = new Paciente("Stura","Sofia","45678945",LocalDate.parse("2023-03-29"),"sturita4@gmail.com",
                new Domicilio("Austria","4567","CABA","Buenos Aires"));
        Odontologo odontologoParaGuardar = new Odontologo("ABC123", "Pereira", "Guillermo");

        Paciente pacienteGuardado = pacienteService.guardarPaciente(pacienteParaGuardar);
        Odontologo odontologoGuardado = odontologoService.guardarOdontologo(odontologoParaGuardar);

        TurnoDTO turnoParaGuardar = new TurnoDTO(LocalDate.parse("2023-04-07"),LocalTime.parse("12:00"),pacienteGuardado, odontologoGuardado );
        TurnoDTO turnoGuardado = turnoService.guardarTurno(turnoParaGuardar);

        assertEquals(1L, turnoGuardado.getPaciente().getId());
        assertEquals(1L, turnoGuardado.getOdontologo().getId());
        assertEquals("45678945", turnoGuardado.getPaciente().getDni());
        assertEquals("ABC123", turnoGuardado.getOdontologo().getMatricula());
        assertEquals(LocalTime.parse("12:00"), turnoGuardado.getHora());
    }

    @Test
    @Order(2)
    public void actualizarTurno() throws ResourceNotFoundException, BadRequestException {
        Optional<TurnoDTO> turnoParaActualizar = turnoService.buscarTurno(1L);
        turnoParaActualizar.get().setFecha(LocalDate.parse("2023-04-10"));

        TurnoDTO turnoActualizado = turnoService.actualizarTurno(turnoParaActualizar.get());

        assertEquals(LocalDate.parse("2023-04-10"), turnoActualizado.getFecha());
    }

    @Test
    @Order(3)
    public void buscarTurnoPorId() throws ResourceNotFoundException, BadRequestException {
        Optional<TurnoDTO> turnoEncontrado = turnoService.buscarTurno(1L);

        assertEquals(1L, turnoEncontrado.get().getPaciente().getId());
    }

    @Test
    @Order(4)
    public void buscarturnos() {
        Collection<TurnoDTO> turnosGuardados = turnoService.buscarTodosLosTurnos();

        assertTrue(1 == turnosGuardados.size());
    }

    @Test
    @Order(5)
    public void eliminarTurno() throws ResourceNotFoundException, BadRequestException {
        turnoService.eliminarTurno(1L);
        Collection<TurnoDTO> turnos = turnoService.buscarTodosLosTurnos();

        assertTrue(turnos.size() == 0);
    }
}