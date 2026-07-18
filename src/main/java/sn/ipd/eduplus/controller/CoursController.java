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
import sn.ipd.eduplus.dto.CoursRequest;
import sn.ipd.eduplus.entity.Cours;
import sn.ipd.eduplus.service.CoursService;

@RestController
@RequestMapping("/cours")
@RequiredArgsConstructor
@Tag(name = "Cours")
@SecurityRequirement(name = "bearerAuth")
public class CoursController {

    private final CoursService coursService;

    @GetMapping
    public ResponseEntity<Page<Cours>> findAll(Pageable pageable) {
        return ResponseEntity.ok(coursService.findAll(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cours> findById(@PathVariable Long id) {
        return ResponseEntity.ok(coursService.findById(id));
    }

    @PostMapping
    public ResponseEntity<Cours> create(@Valid @RequestBody CoursRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(coursService.create(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Cours> update(@PathVariable Long id, @Valid @RequestBody CoursRequest request) {
        return ResponseEntity.ok(coursService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        coursService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
