package fr.openclassrooms.MDD.controllers;

import fr.openclassrooms.MDD.models.Topic;
import fr.openclassrooms.MDD.services.TopicService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/topic")
@RequiredArgsConstructor
public class TopicController {

    private final TopicService topicService;

    @GetMapping
    public ResponseEntity<List<Topic>> getTopics() {
        return ResponseEntity.ok(topicService.getTopics());
    }

    @PutMapping({"/subscribe/{topic}"})
    public ResponseEntity<?> subscribeToTopic(@AuthenticationPrincipal UserDetails userDetails, @PathVariable Topic topic) {
        return ResponseEntity.ok(topicService.subscribeToTopic(userDetails, topic));
    }

    @PutMapping({"/unsubscribe/{topic}"})
    public ResponseEntity<?> unsubscribeToTopic(@AuthenticationPrincipal UserDetails userDetails, @PathVariable Topic topic) {
        return ResponseEntity.ok(topicService.unsubscribeToTopic(userDetails, topic));
    }
}
