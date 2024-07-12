package fr.openclassrooms.MDD.services;

import fr.openclassrooms.MDD.dto.PublicationDto;
import fr.openclassrooms.MDD.dto.PublicationRequest;
import fr.openclassrooms.MDD.models.Publication;
import fr.openclassrooms.MDD.models.Topic;
import fr.openclassrooms.MDD.repositories.PublicationRepository;
import fr.openclassrooms.MDD.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PublicationService {

    private final PublicationRepository publicationRepository;
    private final UserRepository userRepository;

    /**
     * createPublication receives a PublicationRequest object and a UserDetails object.
     * The method retrieves the user from the database using the userRepository bean.
     * The method creates a new Publication object with the information provided in the request.
     * The publication is saved in the database using the publicationRepository bean.
     * The method returns a PublicationDto object.
     * @param publicationRequest - The request object containing the publication information.
     * @param userDetails - The object containing the user information.
     * @return PublicationDto - The response object containing the publication information.
     * */
    public PublicationDto createPublication(PublicationRequest publicationRequest, UserDetails userDetails) {
        var user = userRepository.findByEmailOrUsername(userDetails.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        var publication = Publication.builder()
                .title(publicationRequest.getTitle())
                .content(publicationRequest.getContent())
                .topic(Topic.valueOf(publicationRequest.getTopic()))
                .user(user)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        publication = publicationRepository.save(publication);
        return PublicationDto.builder()
                .id(publication.getId())
                .title(publication.getTitle())
                .content(publication.getContent())
                .author(publication.getUser().toDto())
                .topic(publication.getTopic())
                .created_at(publication.getCreatedAt())
                .updated_at(publication.getUpdatedAt())
                .build();
    }

    /**
     * getAllPublicationsForUser receives a UserDetails object.
     * The method retrieves the user from the database using the userRepository bean.
     * The method retrieves all publications from the database that have a topic that the user is interested in.
     * The method returns a list of PublicationDto objects.
     * @param userDetails - The object containing the user information.
     * @return List<PublicationDto> - The list of response objects containing the publication information.
     * */
    public List<PublicationDto> getAllPublicationsForUser(UserDetails userDetails) {
        var user = userRepository.findByEmailOrUsername(userDetails.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        return publicationRepository.findAllByTopicInOrderByCreatedAtDesc(user.getInterestedTopics())
                .stream()
                .map(Publication::toDto)
                .toList();
    }

    /**
     * getPublicationById receives a publication id.
     * The method retrieves the publication from the database using the publicationRepository bean.
     * The method returns a PublicationDto object.
     * @param id - The id of the publication to retrieve.
     * @return PublicationDto - The response object containing the publication information.
     * */
    public PublicationDto getPublicationById(Long id) {
        return publicationRepository.findById(id)
                .map(Publication::toDto)
                .orElseThrow(() -> new RuntimeException("Publication not found"));
    }
}
