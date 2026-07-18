package sn.ipd.eduplus.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import sn.ipd.eduplus.dto.EtudiantRequest;
import sn.ipd.eduplus.dto.EtudiantResponse;
import sn.ipd.eduplus.service.EtudiantService;

@RestController
@RequestMapping("/etudiants")
@RequiredArgsConstructor
@Tag(name = "Etudiants")
@SecurityRequirement(name = "bearerAuth")
public class EtudiantController {

    private final EtudiantService etudiantService;

    @GetMapping
    public ResponseEntity<Page<EtudiantResponse>> findAll(Pageable pageable) {
        return ResponseEntity.ok(etudiantService.findAll(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<EtudiantResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(etudiantService.findById(id));
    }

    @PostMapping
    public ResponseEntity<EtudiantResponse> create(@Valid @RequestBody EtudiantRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(etudiantService.create(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<EtudiantResponse> update(@PathVariable Long id, @Valid @RequestBody EtudiantRequest request) {
        return ResponseEntity.ok(etudiantService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        etudiantService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping(value = "/{id}/photo", consumes = "multipart/form-data")
    public ResponseEntity<EtudiantResponse> uploadPhoto(@PathVariable Long id, @RequestParam("file") MultipartFile file) {
        return ResponseEntity.ok(etudiantService.uploadPhoto(id, file));
    }

    @PostMapping(value = "/{id}/docs", consumes = "multipart/form-data")
    public ResponseEntity<EtudiantResponse> uploadDocument(@PathVariable Long id, @RequestParam("file") MultipartFile file) {
        return ResponseEntity.ok(etudiantService.uploadDocument(id, file));
    }
}
