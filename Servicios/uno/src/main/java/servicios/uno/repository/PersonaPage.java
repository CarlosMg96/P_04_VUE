package servicios.uno.repository;


import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import servicios.uno.model.Persona;


public interface  PersonaPage extends PagingAndSortingRepository<Persona,Long> {
    @Query("SELECT p FROM Persona p WHERE LOWER(p.name) LIKE LOWER(CONCAT('%', :filtro, '%')) OR LOWER(p.lastname) LIKE LOWER(CONCAT('%', :filtro, '%'))")
    Page<Persona> obtenerPersonasPaginadasConFiltro(@Param("filtro") String filtro, Pageable pageable);
}
