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

    public List<PublicationDto> getAllPublicationsForUser(UserDetails userDetails) {
        var user = userRepository.findByEmailOrUsername(userDetails.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        return publicationRepository.findAllByTopicInOrderByCreatedAtDesc(user.getInterestedTopics())
                .stream()
                .map(Publication::toDto)
                .toList();
    }

    public PublicationDto getPublicationById(Long id) {
        return publicationRepository.findById(id)
                .map(Publication::toDto)
                .orElseThrow(() -> new RuntimeException("Publication not found"));
    }
}
