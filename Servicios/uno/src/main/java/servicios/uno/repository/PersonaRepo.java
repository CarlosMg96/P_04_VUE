package servicios.uno.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import servicios.uno.model.Persona;

public interface  PersonaRepo extends JpaRepository<Persona,Long> {
    @Query("SELECT p FROM Persona p")
    Page<Persona> PageofPersona(Pageable pageable);
}
