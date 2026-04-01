package com.api.backapi.controller;
import com.api.backapi.dto.RendezVousRequest;
import com.api.backapi.dto.RendezVousResponse;
import com.api.backapi.service.RendezVousService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
/**
 * RestController pour gérer les rendez-vous
 */
@RestController
@RequestMapping("/api/appointments")
@RequiredArgsConstructor
@Slf4j
public class RendezVousController {
    private final RendezVousService rendezVousService;
    /**
     * Crée un nouveau rendez-vous
     * POST /api/appointments
     */
    @PostMapping
    public ResponseEntity<RendezVousResponse> createRendezVous(@Valid @RequestBody RendezVousRequest request) {
        log.info("POST /api/appointments - Création d'un rendez-vous");
        RendezVousResponse response = rendezVousService.createRendezVous(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    /**
     * Récupère tous les rendez-vous
     * GET /api/appointments
     */
    @GetMapping
    public ResponseEntity<List<RendezVousResponse>> getAllRendezVous() {
        log.info("GET /api/appointments - Récupération de tous les rendez-vous");
        List<RendezVousResponse> rendezVousList = rendezVousService.getAllRendezVous();
        return ResponseEntity.ok(rendezVousList);
    }
    /**
     * Récupère un rendez-vous par son ID
     * GET /api/appointments/{id}
     */
    @GetMapping("/{id}")
    public ResponseEntity<RendezVousResponse> getRendezVousById(@PathVariable Long id) {
        log.info("GET /api/appointments/{} - Récupération d'un rendez-vous", id);
        RendezVousResponse response = rendezVousService.getRendezVousById(id);
        return ResponseEntity.ok(response);
    }
    /**
     * Met à jour un rendez-vous
     * PUT /api/appointments/{id}
     */
    @PutMapping("/{id}")
    public ResponseEntity<RendezVousResponse> updateRendezVous(
            @PathVariable Long id,
            @Valid @RequestBody RendezVousRequest request) {
        log.info("PUT /api/appointments/{} - Mise à jour d'un rendez-vous", id);
        RendezVousResponse response = rendezVousService.updateRendezVous(id, request);
        return ResponseEntity.ok(response);
    }
    /**
     * Supprime un rendez-vous
     * DELETE /api/appointments/{id}
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRendezVous(@PathVariable Long id) {
        log.info("DELETE /api/appointments/{} - Suppression d'un rendez-vous", id);
        rendezVousService.deleteRendezVous(id);
        return ResponseEntity.noContent().build();
    }
    /**
     * Récupère les rendez-vous par email du client
     * GET /api/appointments/client/{email}
     */
    @GetMapping("/client/{email}")
    public ResponseEntity<List<RendezVousResponse>> getRendezVousByClientEmail(@PathVariable String email) {
        log.info("GET /api/appointments/client/{} - Récupération des rendez-vous par email", email);
        List<RendezVousResponse> response = rendezVousService.getRendezVousByClientEmail(email);
        return ResponseEntity.ok(response);
    }
    /**
     * Endpoint de santé
     * GET /api/appointments/health
     */
    @GetMapping("/health")
    public ResponseEntity<String> health() {
        return ResponseEntity.ok("API is running");
    }
}
