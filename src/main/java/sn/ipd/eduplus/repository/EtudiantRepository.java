package sn.ipd.eduplus.repository;

import sn.ipd.eduplus.entity.Etudiant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EtudiantRepository extends JpaRepository<Etudiant, Long> {
    boolean existsByMatricule(String matricule);
    boolean existsByEmail(String email);
}
