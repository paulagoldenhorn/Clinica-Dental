package com.DH.clinicaDental.controller;

import com.DH.clinicaDental.model.entity.Odontologo;
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
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
public class OdontologoIntegrationTest {
    @Autowired
    private MockMvc mock;
    @Autowired
    private ObjectMapper mapper;

    @Test
    @Order(1)
    public void guardarOdontologoTest() throws Exception {
        Odontologo odontologoParaGuardar = new Odontologo("MT789456", "Benegas", "Walter");
        Odontologo odontologoGuardado = new Odontologo(1L,"MT789456", "Benegas", "Walter");

        MvcResult resultado = mock.perform(
                        MockMvcRequestBuilders.post("/odontologos")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(mapper.writeValueAsString(odontologoParaGuardar))
                                .characterEncoding("utf-8")
                )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(content().contentType("application/json"))
                .andExpect(content().json(mapper.writeValueAsString(odontologoGuardado)))
                .andReturn();

        assertEquals(mapper.writeValueAsString(odontologoGuardado), resultado.getResponse().getContentAsString());
    }

    @Test
    @Order(2)
    public void buscarOdontologoTest() throws Exception {
        Odontologo odontologoGuardado = new Odontologo(1L,"MT789456", "Benegas", "Walter");
        MvcResult resultado = mock.perform(
                        MockMvcRequestBuilders.get("/odontologos/1")
                                .accept(MediaType.APPLICATION_JSON)
                                .characterEncoding("utf-8"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(odontologoGuardado)))
                .andReturn();

        assertEquals(mapper.writeValueAsString(odontologoGuardado), resultado.getResponse().getContentAsString());
    }

    @Test
    @Order(3)
    public void buscarOdontologosTest() throws Exception {
        MvcResult resultado = mock.perform(
                        MockMvcRequestBuilders.get("/odontologos")
                                .accept(MediaType.APPLICATION_JSON)
                                .characterEncoding("utf-8"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        assertFalse(resultado.getResponse().getContentAsString().isEmpty());
    }
}