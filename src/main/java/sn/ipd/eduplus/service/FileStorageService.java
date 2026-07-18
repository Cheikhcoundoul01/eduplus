package sn.ipd.eduplus.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import sn.ipd.eduplus.exception.InvalidFileException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.UUID;

@Service
public class FileStorageService {

    @Value("${file.upload-dir}")
    private String uploadDir;

    private static final long MAX_PHOTO_SIZE = 2L * 1024 * 1024; // 2 Mo
    private static final List<String> ALLOWED_IMAGE_TYPES = List.of("image/jpeg", "image/png");
    private static final long MAX_DOC_SIZE = 5L * 1024 * 1024; // 5 Mo
    private static final List<String> ALLOWED_DOC_TYPES = List.of("application/pdf");

    public String storePhoto(MultipartFile file) {
        validate(file, ALLOWED_IMAGE_TYPES, MAX_PHOTO_SIZE, "La photo doit etre un JPEG ou PNG de 2 Mo maximum");
        return store(file, "photos");
    }

    public String storeDocument(MultipartFile file) {
        validate(file, ALLOWED_DOC_TYPES, MAX_DOC_SIZE, "Le document doit etre un PDF de 5 Mo maximum");
        return store(file, "docs");
    }

    private void validate(MultipartFile file, List<String> allowedTypes, long maxSize, String errorMessage) {
        if (file == null || file.isEmpty()) {
            throw new InvalidFileException("Le fichier envoye est vide");
        }
        if (!allowedTypes.contains(file.getContentType())) {
            throw new InvalidFileException(errorMessage);
        }
        if (file.getSize() > maxSize) {
            throw new InvalidFileException(errorMessage);
        }
    }

    private String store(MultipartFile file, String subDir) {
        try {
            Path targetDir = Paths.get(uploadDir, subDir);
            Files.createDirectories(targetDir);

            String originalName = file.getOriginalFilename() != null ? file.getOriginalFilename() : "fichier";
            String extension = originalName.contains(".")
                    ? originalName.substring(originalName.lastIndexOf('.'))
                    : "";
            String storedName = UUID.randomUUID() + extension;

            Path targetPath = targetDir.resolve(storedName);
            Files.copy(file.getInputStream(), targetPath, StandardCopyOption.REPLACE_EXISTING);

            return "/uploads/" + subDir + "/" + storedName;
        } catch (IOException e) {
            throw new InvalidFileException("Erreur lors de l'enregistrement du fichier");
        }
    }
}
