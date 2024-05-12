package fr.openclassrooms.MDD.controllers;

import fr.openclassrooms.MDD.dto.CommentRequest;
import fr.openclassrooms.MDD.services.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/publication/{publicationId}/comment")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping
    public ResponseEntity<?> createComment(
            @AuthenticationPrincipal UserDetails userDetails,
            @PathVariable Long publicationId,
            @RequestBody CommentRequest commentRequest) {
        return ResponseEntity.ok(commentService.createComment(commentRequest, userDetails, publicationId));
    }
}
