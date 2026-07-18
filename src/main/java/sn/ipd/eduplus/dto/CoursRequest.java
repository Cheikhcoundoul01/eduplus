package sn.ipd.eduplus.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class CoursRequest {

    @NotBlank(message = "Le code du cours est obligatoire")
    private String code;

    @NotBlank(message = "Le nom du cours est obligatoire")
    private String nom;

    @Positive(message = "Les credits doivent etre positifs")
    private int credits;

    @NotNull(message = "L'enseignant est obligatoire")
    private Long enseignantId;
}
