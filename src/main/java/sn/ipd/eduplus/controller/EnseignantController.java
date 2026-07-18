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
import sn.ipd.eduplus.dto.EnseignantRequest;
import sn.ipd.eduplus.entity.Enseignant;
import sn.ipd.eduplus.service.EnseignantService;

@RestController
@RequestMapping("/enseignants")
@RequiredArgsConstructor
@Tag(name = "Enseignants")
@SecurityRequirement(name = "bearerAuth")
public class EnseignantController {

    private final EnseignantService enseignantService;

    @GetMapping
    public ResponseEntity<Page<Enseignant>> findAll(Pageable pageable) {
        return ResponseEntity.ok(enseignantService.findAll(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Enseignant> findById(@PathVariable Long id) {
        return ResponseEntity.ok(enseignantService.findById(id));
    }

    @PostMapping
    public ResponseEntity<Enseignant> create(@Valid @RequestBody EnseignantRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(enseignantService.create(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Enseignant> update(@PathVariable Long id, @Valid @RequestBody EnseignantRequest request) {
        return ResponseEntity.ok(enseignantService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        enseignantService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
