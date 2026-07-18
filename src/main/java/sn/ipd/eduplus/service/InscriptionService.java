package sn.ipd.eduplus.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sn.ipd.eduplus.dto.InscriptionRequest;
import sn.ipd.eduplus.entity.Cours;
import sn.ipd.eduplus.entity.Etudiant;
import sn.ipd.eduplus.entity.Inscription;
import sn.ipd.eduplus.exception.DuplicateResourceException;
import sn.ipd.eduplus.exception.ResourceNotFoundException;
import sn.ipd.eduplus.repository.CoursRepository;
import sn.ipd.eduplus.repository.EtudiantRepository;
import sn.ipd.eduplus.repository.InscriptionRepository;

@Service
@RequiredArgsConstructor
@Transactional
public class InscriptionService {

    private final InscriptionRepository inscriptionRepository;
    private final EtudiantRepository etudiantRepository;
    private final CoursRepository coursRepository;

    public Page<Inscription> findAll(Pageable pageable) {
        return inscriptionRepository.findAll(pageable);
    }

    public Inscription create(InscriptionRequest request) {
        if (inscriptionRepository.existsByEtudiantIdAndCoursId(request.getEtudiantId(), request.getCoursId())) {
            throw new DuplicateResourceException("Cet etudiant est deja inscrit a ce cours");
        }

        Etudiant etudiant = etudiantRepository.findById(request.getEtudiantId())
                .orElseThrow(() -> new ResourceNotFoundException("Etudiant introuvable avec l'id " + request.getEtudiantId()));
        Cours cours = coursRepository.findById(request.getCoursId())
                .orElseThrow(() -> new ResourceNotFoundException("Cours introuvable avec l'id " + request.getCoursId()));

        Inscription inscription = Inscription.builder()
                .etudiant(etudiant)
                .cours(cours)
                .build();

        return inscriptionRepository.save(inscription);
    }

    public void delete(Long id) {
        Inscription inscription = inscriptionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Inscription introuvable avec l'id " + id));
        inscriptionRepository.delete(inscription);
    }
}
