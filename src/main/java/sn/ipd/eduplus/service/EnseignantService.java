package sn.ipd.eduplus.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sn.ipd.eduplus.dto.EnseignantRequest;
import sn.ipd.eduplus.entity.Enseignant;
import sn.ipd.eduplus.exception.ResourceNotFoundException;
import sn.ipd.eduplus.repository.EnseignantRepository;

@Service
@RequiredArgsConstructor
@Transactional
public class EnseignantService {

    private final EnseignantRepository enseignantRepository;

    public Page<Enseignant> findAll(Pageable pageable) {
        return enseignantRepository.findAll(pageable);
    }

    public Enseignant findById(Long id) {
        return getOrThrow(id);
    }

    public Enseignant create(EnseignantRequest request) {
        Enseignant enseignant = Enseignant.builder()
                .nom(request.getNom())
                .prenom(request.getPrenom())
                .email(request.getEmail())
                .specialite(request.getSpecialite())
                .build();
        return enseignantRepository.save(enseignant);
    }

    public Enseignant update(Long id, EnseignantRequest request) {
        Enseignant enseignant = getOrThrow(id);
        enseignant.setNom(request.getNom());
        enseignant.setPrenom(request.getPrenom());
        enseignant.setEmail(request.getEmail());
        enseignant.setSpecialite(request.getSpecialite());
        return enseignantRepository.save(enseignant);
    }

    public void delete(Long id) {
        enseignantRepository.delete(getOrThrow(id));
    }

    private Enseignant getOrThrow(Long id) {
        return enseignantRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Enseignant introuvable avec l'id " + id));
    }
}
