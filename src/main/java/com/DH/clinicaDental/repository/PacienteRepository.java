package com.DH.clinicaDental.repository;

import com.DH.clinicaDental.model.entity.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PacienteRepository extends JpaRepository<Paciente, Long> {
    Optional<Paciente> findByEmail(String correo);

    // Prohibir registros con el mismo dni o email
    boolean existsPacienteByDni(String dni);
    boolean existsPacienteByEmail(String email);
}
