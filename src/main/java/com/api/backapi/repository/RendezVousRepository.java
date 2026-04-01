package com.api.backapi.repository;
import com.api.backapi.entity.RendezVous;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;
/**
 * Repository pour l'Entity RendezVous
 */
@Repository
public interface RendezVousRepository extends JpaRepository<RendezVous, Long> {
    /**
     * Récupère les rendez-vous par email du client
     */
    List<RendezVous> findByClientEmail(String clientEmail);
    /**
     * Récupère les rendez-vous par nom du client
     */
    List<RendezVous> findByClientName(String clientName);
    /**
     * Récupère les rendez-vous entre deux dates
     */
    List<RendezVous> findByDateTimeBetween(LocalDateTime start, LocalDateTime end);
}
