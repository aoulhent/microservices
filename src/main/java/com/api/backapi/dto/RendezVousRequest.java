package com.api.backapi.dto;
import com.api.backapi.entity.AppointmentStatus;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
/**
 * DTO pour la création/mise à jour d'un rendez-vous
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RendezVousRequest {
    @NotBlank(message = "Le nom du client est obligatoire")
    private String clientName;
    @NotBlank(message = "L'email du client est obligatoire")
    @Email(message = "L'email du client doit être valide")
    private String clientEmail;
    @NotNull(message = "La date et l'heure du rendez-vous sont obligatoires")
    private LocalDateTime dateTime;
    private String description;
    @Builder.Default
    private AppointmentStatus status = AppointmentStatus.SCHEDULED;
}
