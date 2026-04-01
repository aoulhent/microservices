package com.api.backapi.service;
import com.api.backapi.dto.RendezVousRequest;
import com.api.backapi.dto.RendezVousResponse;
import com.api.backapi.entity.RendezVous;
import com.api.backapi.exception.ResourceNotFoundException;
import com.api.backapi.mapper.RendezVousMapper;
import com.api.backapi.repository.RendezVousRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;
/**
 * Implémentation du service RendezVousService
 */
@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class RendezVousServiceImpl implements RendezVousService {
    private final RendezVousRepository rendezVousRepository;
    private final RendezVousMapper rendezVousMapper;
    @Override
    public RendezVousResponse createRendezVous(RendezVousRequest request) {
        log.info("Création d'un nouveau rendez-vous pour le client: {}", request.getClientName());
        RendezVous rendezVous = rendezVousMapper.toEntity(request);
        RendezVous saved = rendezVousRepository.save(rendezVous);
        log.info("Rendez-vous créé avec l'ID: {}", saved.getId());
        return rendezVousMapper.toResponse(saved);
    }
    @Override
    @Transactional(readOnly = true)
    public List<RendezVousResponse> getAllRendezVous() {
        log.info("Récupération de tous les rendez-vous");
        return rendezVousRepository.findAll()
                .stream()
                .map(rendezVousMapper::toResponse)
                .collect(Collectors.toList());
    }
    @Override
    @Transactional(readOnly = true)
    public RendezVousResponse getRendezVousById(Long id) {
        log.info("Récupération du rendez-vous avec l'ID: {}", id);
        RendezVous rendezVous = rendezVousRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Rendez-vous avec l'ID {} non trouvé", id);
                    return new ResourceNotFoundException("Rendez-vous avec l'ID " + id + " non trouvé");
                });
        return rendezVousMapper.toResponse(rendezVous);
    }
    @Override
    public RendezVousResponse updateRendezVous(Long id, RendezVousRequest request) {
        log.info("Mise à jour du rendez-vous avec l'ID: {}", id);
        RendezVous rendezVous = rendezVousRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Rendez-vous avec l'ID {} non trouvé", id);
                    return new ResourceNotFoundException("Rendez-vous avec l'ID " + id + " non trouvé");
                });
        rendezVousMapper.updateEntityFromRequest(request, rendezVous);
        RendezVous updated = rendezVousRepository.save(rendezVous);
        log.info("Rendez-vous avec l'ID {} mis à jour", id);
        return rendezVousMapper.toResponse(updated);
    }
    @Override
    public void deleteRendezVous(Long id) {
        log.info("Suppression du rendez-vous avec l'ID: {}", id);
        if (!rendezVousRepository.existsById(id)) {
            log.error("Rendez-vous avec l'ID {} non trouvé", id);
            throw new ResourceNotFoundException("Rendez-vous avec l'ID " + id + " non trouvé");
        }
        rendezVousRepository.deleteById(id);
        log.info("Rendez-vous avec l'ID {} supprimé", id);
    }
    @Override
    @Transactional(readOnly = true)
    public List<RendezVousResponse> getRendezVousByClientEmail(String email) {
        log.info("Récupération des rendez-vous pour l'email: {}", email);
        return rendezVousRepository.findByClientEmail(email)
                .stream()
                .map(rendezVousMapper::toResponse)
                .collect(Collectors.toList());
    }
}
