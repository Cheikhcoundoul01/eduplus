package sn.ipd.eduplus.repository;

import sn.ipd.eduplus.entity.Inscription;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InscriptionRepository extends JpaRepository<Inscription, Long> {
    boolean existsByEtudiantIdAndCoursId(Long etudiantId, Long coursId);
}
