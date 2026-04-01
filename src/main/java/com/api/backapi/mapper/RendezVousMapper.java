package com.api.backapi.mapper;
import com.api.backapi.dto.RendezVousRequest;
import com.api.backapi.dto.RendezVousResponse;
import com.api.backapi.entity.RendezVous;
import org.springframework.stereotype.Component;
/**
 * Mapper manuel pour convertir entre Entity et DTOs
 */
@Component
public class RendezVousMapper {
    /**
     * Convertit une requête en Entity
     */
    public RendezVous toEntity(RendezVousRequest request) {
        if (request == null) {
            return null;
        }
        return RendezVous.builder()
                .clientName(request.getClientName())
                .clientEmail(request.getClientEmail())
                .dateTime(request.getDateTime())
                .description(request.getDescription())
                .status(request.getStatus())
                .build();
    }
    /**
     * Convertit une Entity en réponse
     */
    public RendezVousResponse toResponse(RendezVous entity) {
        if (entity == null) {
            return null;
        }
        return RendezVousResponse.builder()
                .id(entity.getId())
                .clientName(entity.getClientName())
                .clientEmail(entity.getClientEmail())
                .dateTime(entity.getDateTime())
                .description(entity.getDescription())
                .status(entity.getStatus())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }
    /**
     * Met à jour une Entity à partir d'une requête
     */
    public void updateEntityFromRequest(RendezVousRequest request, RendezVous entity) {
        if (request == null || entity == null) {
            return;
        }
        entity.setClientName(request.getClientName());
        entity.setClientEmail(request.getClientEmail());
        entity.setDateTime(request.getDateTime());
        entity.setDescription(request.getDescription());
        entity.setStatus(request.getStatus());
    }
}
