package com.DH.clinicaDental.model.dto;

import com.DH.clinicaDental.model.entity.Odontologo;
import com.DH.clinicaDental.model.entity.Paciente;

import java.time.LocalDate;
import java.time.LocalTime;

public class TurnoDTO {
    private Long id;
    private LocalDate fecha;
    private LocalTime hora;
    private Paciente paciente;
    private Odontologo odontologo;

    public TurnoDTO() {
    }

    public TurnoDTO(Long id, LocalDate fecha, LocalTime hora, Paciente paciente, Odontologo odontologo) {
        this.id = id;
        this.fecha = fecha;
        this.hora = hora;
        this.paciente = paciente;
        this.odontologo = odontologo;
    }

    public TurnoDTO(LocalDate fecha, LocalTime hora, Paciente paciente, Odontologo odontologo) {
        this.fecha = fecha;
        this.hora = hora;
        this.paciente = paciente;
        this.odontologo = odontologo;
    }

    public Paciente getPaciente() {
        return paciente;
    }

    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }

    public Odontologo getOdontologo() {
        return odontologo;
    }

    public void setOdontologo(Odontologo odontologo) {
        this.odontologo = odontologo;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public LocalTime getHora() {
        return hora;
    }

    public void setHora(LocalTime hora) {
        this.hora = hora;
    }

}
