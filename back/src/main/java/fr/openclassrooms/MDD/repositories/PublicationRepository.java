package fr.openclassrooms.MDD.repositories;

import fr.openclassrooms.MDD.models.Publication;
import fr.openclassrooms.MDD.models.Topic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface PublicationRepository extends JpaRepository<Publication, Long> {

    List<Publication> findAllByTopicInOrderByCreatedAtDesc(Set<Topic> topics);

}
