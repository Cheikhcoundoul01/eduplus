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
import sn.ipd.eduplus.dto.InscriptionRequest;
import sn.ipd.eduplus.entity.Inscription;
import sn.ipd.eduplus.service.InscriptionService;

@RestController
@RequestMapping("/inscriptions")
@RequiredArgsConstructor
@Tag(name = "Inscriptions")
@SecurityRequirement(name = "bearerAuth")
public class InscriptionController {

    private final InscriptionService inscriptionService;

    @GetMapping
    public ResponseEntity<Page<Inscription>> findAll(Pageable pageable) {
        return ResponseEntity.ok(inscriptionService.findAll(pageable));
    }

    @PostMapping
    public ResponseEntity<Inscription> create(@Valid @RequestBody InscriptionRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(inscriptionService.create(request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        inscriptionService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
