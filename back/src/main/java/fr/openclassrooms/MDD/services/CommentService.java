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

    /**
     * createComment receives a CommentRequest object, a UserDetails object, and a publicationId.
     * The method retrieves the user from the database using the userRepository bean.
     * The method retrieves the publication from the database using the publicationRepository bean.
     * The method creates a new Comment object with the information provided in the request.
     * The comment is saved in the database using the commentRepository bean.
     * The method retrieves the publication from the database using the publicationRepository bean.
     * The method returns a PublicationDto object.
     * @param commentRequest - The request object containing the comment information.
     * @param userDetails - The object containing the user information.
     * @param publicationId - The id of the publication where the comment will be added.
     * @return PublicationDto - The response object containing the publication information.
     * */
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
