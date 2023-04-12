package com.DH.clinicaDental.controller;

import com.DH.clinicaDental.model.dto.TurnoDTO;
import com.DH.clinicaDental.model.entity.Domicilio;
import com.DH.clinicaDental.model.entity.Odontologo;
import com.DH.clinicaDental.model.entity.Paciente;
import com.DH.clinicaDental.service.IOdontologoService;
import com.DH.clinicaDental.service.IPacienteService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
public class TurnoIntegrationTest {
    @Autowired
    private MockMvc mock;
    @Autowired
    private IPacienteService pacienteService;
    @Autowired
    private IOdontologoService odontologoService;
    @Autowired
    private ObjectMapper mapper;

    @Test
    @Order(1)
    public void guardarTurnoTest() throws Exception {
        Odontologo odontologoParaGuardar = new Odontologo("MT789456", "Benegas", "Walter");
        Paciente pacienteParaGuardar = new Paciente("Stura", "Sofia", "45678914", LocalDate.parse("2023-03-12"), "sturita@gmail.com",
                new Domicilio("Forest", "1234", "CABA", "Buenos Aires"));
        Paciente pacienteGuardado = pacienteService.guardarPaciente(pacienteParaGuardar);
        Odontologo odontologoGuardado = odontologoService.guardarOdontologo(odontologoParaGuardar);

        TurnoDTO turnoParaGuardar = new TurnoDTO(LocalDate.parse("2023-04-15"), LocalTime.parse("12:30"), pacienteGuardado, odontologoGuardado);
        TurnoDTO turnoGuardardado = new TurnoDTO(1L, LocalDate.parse("2023-04-15"), LocalTime.parse("12:30"), pacienteGuardado, odontologoGuardado);

        MvcResult resultado = mock.perform(
                        MockMvcRequestBuilders.post("/turnos")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(mapper.writeValueAsString(turnoParaGuardar))
                                .characterEncoding("utf-8")
                )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(content().contentType("application/json"))
                .andExpect(content().json(mapper.writeValueAsString(turnoGuardardado)))
                .andReturn();

        assertEquals(mapper.writeValueAsString(turnoGuardardado), resultado.getResponse().getContentAsString());
    }

    @Test
    @Order(2)
    public void buscarTurnosTest() throws Exception {
        MvcResult resultado = mock.perform(
                MockMvcRequestBuilders.get("/turnos")
                .accept(MediaType.APPLICATION_JSON)
                .characterEncoding("utf-8"))
        .andDo(MockMvcResultHandlers.print())
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andReturn();

        assertFalse(resultado.getResponse().getContentAsString().isEmpty());
    }

    @Test
    @Order(3)
    public void buscarTurnoTest() throws Exception {
        Odontologo odontologoParaGuardar = new Odontologo(1L, "MT789456", "Benegas", "Walter");
        Paciente pacienteParaGuardar = new Paciente(1L,"Stura", "Sofia", "45678914", LocalDate.parse("2023-03-12"), "sturita@gmail.com",
                new Domicilio(1L,"Forest", "1234", "CABA", "Buenos Aires"));

        TurnoDTO turnoGuardado = new TurnoDTO(1L, LocalDate.parse("2023-04-15"), LocalTime.parse("12:30"), pacienteParaGuardar, odontologoParaGuardar);

        MvcResult resultado = mock.perform(
                        MockMvcRequestBuilders.get("/turnos/1")
                                .accept(MediaType.APPLICATION_JSON)
                                .characterEncoding("utf-8"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(turnoGuardado)))
                .andReturn();

        assertEquals(mapper.writeValueAsString(turnoGuardado), resultado.getResponse().getContentAsString());
    }
}
