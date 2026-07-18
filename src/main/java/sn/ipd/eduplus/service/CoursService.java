package sn.ipd.eduplus.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sn.ipd.eduplus.dto.CoursRequest;
import sn.ipd.eduplus.entity.Cours;
import sn.ipd.eduplus.entity.Enseignant;
import sn.ipd.eduplus.exception.DuplicateResourceException;
import sn.ipd.eduplus.exception.ResourceNotFoundException;
import sn.ipd.eduplus.repository.CoursRepository;
import sn.ipd.eduplus.repository.EnseignantRepository;

@Service
@RequiredArgsConstructor
@Transactional
public class CoursService {

    private final CoursRepository coursRepository;
    private final EnseignantRepository enseignantRepository;

    public Page<Cours> findAll(Pageable pageable) {
        return coursRepository.findAll(pageable);
    }

    public Cours findById(Long id) {
        return getOrThrow(id);
    }

    public Cours create(CoursRequest request) {
        if (coursRepository.existsByCode(request.getCode())) {
            throw new DuplicateResourceException("Ce code de cours est deja utilise");
        }
        Enseignant enseignant = enseignantRepository.findById(request.getEnseignantId())
                .orElseThrow(() -> new ResourceNotFoundException("Enseignant introuvable avec l'id " + request.getEnseignantId()));

        Cours cours = Cours.builder()
                .code(request.getCode())
                .nom(request.getNom())
                .credits(request.getCredits())
                .enseignant(enseignant)
                .build();
        return coursRepository.save(cours);
    }

    public Cours update(Long id, CoursRequest request) {
        Cours cours = getOrThrow(id);
        Enseignant enseignant = enseignantRepository.findById(request.getEnseignantId())
                .orElseThrow(() -> new ResourceNotFoundException("Enseignant introuvable avec l'id " + request.getEnseignantId()));

        cours.setCode(request.getCode());
        cours.setNom(request.getNom());
        cours.setCredits(request.getCredits());
        cours.setEnseignant(enseignant);
        return coursRepository.save(cours);
    }

    public void delete(Long id) {
        coursRepository.delete(getOrThrow(id));
    }

    private Cours getOrThrow(Long id) {
        return coursRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cours introuvable avec l'id " + id));
    }
}
