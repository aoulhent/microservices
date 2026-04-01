package com.api.backapi.service;
import com.api.backapi.dto.RendezVousRequest;
import com.api.backapi.dto.RendezVousResponse;
import java.util.List;
/**
 * Interface du service pour gérer les rendez-vous
 */
public interface RendezVousService {
    /**
     * Crée un nouveau rendez-vous
     */
    RendezVousResponse createRendezVous(RendezVousRequest request);
    /**
     * Récupère tous les rendez-vous
     */
    List<RendezVousResponse> getAllRendezVous();
    /**
     * Récupère un rendez-vous par son ID
     */
    RendezVousResponse getRendezVousById(Long id);
    /**
     * Met à jour un rendez-vous
     */
    RendezVousResponse updateRendezVous(Long id, RendezVousRequest request);
    /**
     * Supprime un rendez-vous
     */
    void deleteRendezVous(Long id);
    /**
     * Récupère les rendez-vous par email du client
     */
    List<RendezVousResponse> getRendezVousByClientEmail(String email);
}
