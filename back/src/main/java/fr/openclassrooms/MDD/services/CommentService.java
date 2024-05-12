package fr.openclassrooms.MDD.services;

import fr.openclassrooms.MDD.dto.CommentRequest;
import fr.openclassrooms.MDD.dto.PublicationDto;
import fr.openclassrooms.MDD.models.Comment;
import fr.openclassrooms.MDD.repositories.CommentRepository;
import fr.openclassrooms.MDD.repositories.PublicationRepository;
import fr.openclassrooms.MDD.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final PublicationRepository publicationRepository;
    private final UserRepository userRepository;
    private final CommentRepository commentRepository;

    public PublicationDto createComment(CommentRequest commentRequest, UserDetails userDetails, Long publicationId) {
        var user = userRepository.findByEmailOrUsername(userDetails.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        var publication = publicationRepository.findById(publicationId)
                .orElseThrow(() -> new RuntimeException("Publication not found"));

        var comment = Comment.builder()
                .content(commentRequest.getContent())
                .publication(publication)
                .user(user)
                .createdAt(LocalDateTime.now())
                .updatedAt(null)
                .build();

        comment = commentRepository.save(comment);

        publication = publicationRepository.findById(publicationId)
                .orElseThrow(() -> new RuntimeException("Publication not found"));

        return publication.toDto();
    }
}
