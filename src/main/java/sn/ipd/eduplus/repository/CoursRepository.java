package sn.ipd.eduplus.repository;

import sn.ipd.eduplus.entity.Cours;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CoursRepository extends JpaRepository<Cours, Long> {
    boolean existsByCode(String code);
}
