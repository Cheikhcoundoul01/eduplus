package sn.ipd.eduplus.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import sn.ipd.eduplus.dto.EtudiantRequest;
import sn.ipd.eduplus.dto.EtudiantResponse;
import sn.ipd.eduplus.entity.Etudiant;
import sn.ipd.eduplus.exception.DuplicateResourceException;
import sn.ipd.eduplus.exception.ResourceNotFoundException;
import sn.ipd.eduplus.repository.EtudiantRepository;

@Service
@RequiredArgsConstructor
@Transactional
public class EtudiantService {

    private final EtudiantRepository etudiantRepository;
    private final FileStorageService fileStorageService;

    public Page<EtudiantResponse> findAll(Pageable pageable) {
        return etudiantRepository.findAll(pageable).map(this::toResponse);
    }

    public EtudiantResponse findById(Long id) {
        return toResponse(getOrThrow(id));
    }

    public EtudiantResponse create(EtudiantRequest request) {
        if (etudiantRepository.existsByMatricule(request.getMatricule())) {
            throw new DuplicateResourceException("Ce matricule est deja utilise");
        }
        if (etudiantRepository.existsByEmail(request.getEmail())) {
            throw new DuplicateResourceException("Cet email est deja utilise");
        }

        Etudiant etudiant = Etudiant.builder()
                .matricule(request.getMatricule())
                .nom(request.getNom())
                .prenom(request.getPrenom())
                .email(request.getEmail())
                .dateNaissance(request.getDateNaissance())
                .build();

        return toResponse(etudiantRepository.save(etudiant));
    }

    public EtudiantResponse update(Long id, EtudiantRequest request) {
        Etudiant etudiant = getOrThrow(id);
        etudiant.setMatricule(request.getMatricule());
        etudiant.setNom(request.getNom());
        etudiant.setPrenom(request.getPrenom());
        etudiant.setEmail(request.getEmail());
        etudiant.setDateNaissance(request.getDateNaissance());
        return toResponse(etudiantRepository.save(etudiant));
    }

    public void delete(Long id) {
        Etudiant etudiant = getOrThrow(id);
        etudiantRepository.delete(etudiant);
    }

    public EtudiantResponse uploadPhoto(Long id, MultipartFile file) {
        Etudiant etudiant = getOrThrow(id);
        String url = fileStorageService.storePhoto(file);
        etudiant.setPhotoUrl(url);
        return toResponse(etudiantRepository.save(etudiant));
    }

    public EtudiantResponse uploadDocument(Long id, MultipartFile file) {
        Etudiant etudiant = getOrThrow(id);
        String url = fileStorageService.storeDocument(file);
        etudiant.setDocumentUrl(url);
        return toResponse(etudiantRepository.save(etudiant));
    }

    private Etudiant getOrThrow(Long id) {
        return etudiantRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Etudiant introuvable avec l'id " + id));
    }

    private EtudiantResponse toResponse(Etudiant e) {
        return EtudiantResponse.builder()
                .id(e.getId())
                .matricule(e.getMatricule())
                .nom(e.getNom())
                .prenom(e.getPrenom())
                .email(e.getEmail())
                .dateNaissance(e.getDateNaissance())
                .photoUrl(e.getPhotoUrl())
                .documentUrl(e.getDocumentUrl())
                .build();
    }
}
