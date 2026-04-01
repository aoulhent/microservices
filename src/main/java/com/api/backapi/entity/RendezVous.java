package com.api.backapi.entity;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
/**
 * Entity représentant un rendez-vous
 */
@Entity
@Table(name = "rendez_vous")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RendezVous {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    /**
     * Nom du client
     */
    @NotBlank(message = "Le nom du client ne peut pas être vide")
    @Column(nullable = false)
    private String clientName;
    /**
     * Email du client
     */
    @NotBlank(message = "L'email du client ne peut pas être vide")
    @Email(message = "L'email du client doit être valide")
    @Column(nullable = false)
    private String clientEmail;
    /**
     * Date et heure du rendez-vous
     */
    @NotNull(message = "La date et l'heure du rendez-vous ne peuvent pas être nulles")
    @Column(nullable = false)
    private LocalDateTime dateTime;
    /**
     * Description du rendez-vous
     */
    @Column(columnDefinition = "TEXT")
    private String description;
    /**
     * Statut du rendez-vous
     */
    @NotNull(message = "Le statut du rendez-vous ne peut pas être nul")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Builder.Default
    private AppointmentStatus status = AppointmentStatus.SCHEDULED;
    /**
     * Date et heure de création du rendez-vous
     */
    @NotNull(message = "La date de création ne peut pas être nulle")
    @Column(nullable = false, updatable = false)
    @Builder.Default
    private LocalDateTime createdAt = LocalDateTime.now();
    /**
     * Date et heure de la dernière modification
     */
    @Column
    private LocalDateTime updatedAt;
    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
