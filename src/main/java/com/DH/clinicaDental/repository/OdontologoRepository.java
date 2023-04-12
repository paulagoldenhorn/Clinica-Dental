package com.DH.clinicaDental.repository;

import com.DH.clinicaDental.model.entity.Odontologo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OdontologoRepository extends JpaRepository<Odontologo, Long> {
    // Prohibir registros con la misma matricula
    boolean existsOdontologoByMatricula(String matricula);
}
