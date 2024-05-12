package fr.openclassrooms.MDD.controllers;


import fr.openclassrooms.MDD.dto.PublicationDto;
import fr.openclassrooms.MDD.dto.PublicationRequest;
import fr.openclassrooms.MDD.services.PublicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/publication")
@RequiredArgsConstructor
public class PublicationController {

    private final PublicationService publicationService;

    @PostMapping
    public ResponseEntity<?> createPublication(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestBody PublicationRequest publicationRequest
    ) {
        PublicationDto publicationDto = publicationService.createPublication(publicationRequest, userDetails);
        return ResponseEntity.ok(publicationDto);
    }

    @GetMapping
    public ResponseEntity<?> getAllPublicationsForUser(
            @AuthenticationPrincipal UserDetails userDetails
    ) {
        return ResponseEntity.ok(publicationService.getAllPublicationsForUser(userDetails));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getPublicationById(
            @PathVariable Long id
    ) {
        return ResponseEntity.ok(publicationService.getPublicationById(id));
    }
}
