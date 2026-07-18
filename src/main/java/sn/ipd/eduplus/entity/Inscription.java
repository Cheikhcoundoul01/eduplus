package sn.ipd.eduplus.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "inscriptions", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"etudiant_id", "cours_id"})
})
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Inscription {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "etudiant_id")
    private Etudiant etudiant;

    @ManyToOne(optional = false)
    @JoinColumn(name = "cours_id")
    private Cours cours;

    @Builder.Default
    private LocalDateTime dateInscription = LocalDateTime.now();
}
