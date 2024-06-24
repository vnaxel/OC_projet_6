package fr.openclassrooms.MDD.utils;

import fr.openclassrooms.MDD.models.Publication;
import fr.openclassrooms.MDD.models.Topic;
import fr.openclassrooms.MDD.models.User;
import fr.openclassrooms.MDD.repositories.PublicationRepository;
import fr.openclassrooms.MDD.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Set;

@Component
@RequiredArgsConstructor
@Slf4j
public class DataLoader implements CommandLineRunner {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final PublicationRepository publicationRepository;

    @Override
    public void run(String... args) throws Exception {
        if (userRepository.count() == 0) {
            User richardDoe = User
                    .builder()
                    .username("john_doe")
                    .email("john.doe@example.com")
                    .password(passwordEncoder.encode("joHn@123"))
                    .interestedTopics(Set.of(Topic.SPRING, Topic.JAVA, Topic.JAVASCRIPT))
                    .createdAt(LocalDateTime.now())
                    .updatedAt(LocalDateTime.now())
                    .build();
            userRepository.save(richardDoe);
            log.debug("created user - {}", richardDoe);

            Publication springPublication = Publication
                    .builder()
                    .title("Understanding Spring Boot Annotations")
                    .content("Spring Boot annotations are a key feature for simplifying Java development. They help to avoid boilerplate code and provide an efficient way to configure your application. This article explores common Spring Boot annotations and their usage in creating RESTful web services...")
                    .user(richardDoe)
                    .topic(Topic.SPRING)
                    .createdAt(LocalDateTime.now().minusDays(3))
                    .updatedAt(LocalDateTime.now())
                    .build();
            publicationRepository.save(springPublication);

            Publication javaPublication = Publication
                    .builder()
                    .title("Mastering Java Streams API")
                    .content("The Streams API in Java 8 provides a powerful and flexible way to handle data collections. This article covers the basics of streams, intermediate and terminal operations, and how to utilize them for efficient data processing in Java applications...")
                    .user(richardDoe)
                    .topic(Topic.JAVA)
                    .createdAt(LocalDateTime.now().minusDays(1))
                    .updatedAt(LocalDateTime.now())
                    .build();
            publicationRepository.save(javaPublication);

            Publication awsPublication = Publication
                    .builder()
                    .title("Deploying Applications with AWS Elastic Beanstalk")
                    .content("AWS Elastic Beanstalk is a platform as a service (PaaS) offered by Amazon Web Services. It enables developers to deploy and manage applications without worrying about the infrastructure. This article provides a step-by-step guide on deploying a Java web application using AWS Elastic Beanstalk...")
                    .user(richardDoe)
                    .topic(Topic.AWS)
                    .createdAt(LocalDateTime.now().minusDays(2))
                    .updatedAt(LocalDateTime.now())
                    .build();
            publicationRepository.save(awsPublication);

            Publication javascriptPublication = Publication
                    .builder()
                    .title("Introduction to Asynchronous JavaScript")
                    .content("Asynchronous JavaScript is a critical concept for developing responsive web applications. This article explains the event loop, callbacks, promises, and async/await, providing practical examples to illustrate how they can be used to manage asynchronous operations in JavaScript...")
                    .user(richardDoe)
                    .topic(Topic.JAVASCRIPT)
                    .createdAt(LocalDateTime.now().minusDays(4))
                    .updatedAt(LocalDateTime.now())
                    .build();
            publicationRepository.save(javascriptPublication);
        }
    }
}
