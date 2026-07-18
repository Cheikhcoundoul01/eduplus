package sn.ipd.eduplus.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class InscriptionRequest {

    @NotNull(message = "L'etudiant est obligatoire")
    private Long etudiantId;

    @NotNull(message = "Le cours est obligatoire")
    private Long coursId;
}
