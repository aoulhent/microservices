package com.api.backapi.dto;
import com.api.backapi.entity.AppointmentStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
/**
 * DTO pour la réponse d'un rendez-vous
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RendezVousResponse {
    private Long id;
    private String clientName;
    private String clientEmail;
    private LocalDateTime dateTime;
    private String description;
    private AppointmentStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
