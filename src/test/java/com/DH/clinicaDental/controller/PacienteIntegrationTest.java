package com.DH.clinicaDental.controller;

import com.DH.clinicaDental.model.entity.Domicilio;
import com.DH.clinicaDental.model.entity.Paciente;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
public class PacienteIntegrationTest {
    @Autowired
    private MockMvc mock;
    @Autowired
    private ObjectMapper mapper;

    @Test
    @Order(1)
    public void guardarPacienteTest() throws Exception {
        Paciente pacienteParaGuardar = new Paciente("Stura", "Sofia", "456789123", LocalDate.parse("2023-03-15"), "sturita@gmail.com",
                new Domicilio("La Pampa", "1234", "CABA", "Buenos Aires"));
        Paciente pacienteGuardado = new Paciente(1L,"Stura", "Sofia", "456789123", LocalDate.parse("2023-03-15"), "sturita@gmail.com",
                new Domicilio(1L,"La Pampa", "1234", "CABA", "Buenos Aires"));

        MvcResult resultado = mock.perform(
                        MockMvcRequestBuilders.post("/pacientes")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(mapper.writeValueAsString(pacienteParaGuardar))
                                .characterEncoding("utf-8")
                )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(content().contentType("application/json"))
                .andExpect(content().json(mapper.writeValueAsString(pacienteGuardado)))
                .andReturn();

        assertEquals(mapper.writeValueAsString(pacienteGuardado), resultado.getResponse().getContentAsString());
    }

    @Test
    @Order(2)
    public void buscarPacienteTest() throws Exception {
        Paciente pacienteGuardado = new Paciente(1L,"Stura", "Sofia", "456789123", LocalDate.parse("2023-03-15"), "sturita@gmail.com",
                new Domicilio(1L,"La Pampa", "1234", "CABA", "Buenos Aires"));

        MvcResult resultado = mock.perform(
                        MockMvcRequestBuilders.get("/pacientes/1")
                                .accept(MediaType.APPLICATION_JSON)
                                .characterEncoding("utf-8"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(pacienteGuardado)))
                .andReturn();

        assertEquals(mapper.writeValueAsString(pacienteGuardado), resultado.getResponse().getContentAsString());
    }

    @Test
    @Order(3)
    public void buscarPacientesTest() throws Exception {
        MvcResult resultado = mock.perform(
                        MockMvcRequestBuilders.get("/pacientes")
                                .accept(MediaType.APPLICATION_JSON)
                                .characterEncoding("utf-8"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        assertFalse(resultado.getResponse().getContentAsString().isEmpty());
    }
}
