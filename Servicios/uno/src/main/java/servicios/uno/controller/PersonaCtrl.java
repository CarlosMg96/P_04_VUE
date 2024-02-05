package servicios.uno.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import servicios.uno.model.Persona;
import servicios.uno.service.PersonaService;

@RestController
@RequestMapping("/api/personas")
@CrossOrigin(origins = "http://localhost:5173")  // Especifica el origen permitido
public class PersonaCtrl {

    @Autowired
    PersonaService service;

    @GetMapping
    public List<Persona> getAll() {
        return service.getListPersona();
    }

    @GetMapping("/{id}")
    public Persona getByIdPersona(@PathVariable Long id) {
        return service.getById(id).orElse(null);
    }

    @PostMapping
    public Persona savePersona(@RequestBody Persona bean) {
        return service.savePersona(bean);
    }

    @PostMapping("/page")
    public ResponseEntity<?> getAll(@RequestBody Map<String, Object> request) {
        try {
            int page = (int) request.getOrDefault("page", 0);
            int size = (int) request.getOrDefault("size", 10);
            String filtro = (String) request.getOrDefault("filtro", "");

            Page<Persona> personasPage;

            // Manejar la paginación y el filtro si se proporcionan
            if (!filtro.isEmpty()) {
                personasPage = service.obtenerPersonasPaginadasConFiltro(filtro, PageRequest.of(page, size, Sort.by("name")));
            } else {
                personasPage = service.PageofPersona(PageRequest.of(page, size, Sort.by("name")));
            }

            return ResponseEntity.ok(personasPage);
        } catch (Exception e) {
            // Manejar errores y devolver una respuesta de error apropiada
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al obtener personas.");
        }
    }

}
