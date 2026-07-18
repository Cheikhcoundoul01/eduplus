package sn.ipd.eduplus.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EtudiantResponse {
    private Long id;
    private String matricule;
    private String nom;
    private String prenom;
    private String email;
    private LocalDate dateNaissance;
    private String photoUrl;
    private String documentUrl;
}
