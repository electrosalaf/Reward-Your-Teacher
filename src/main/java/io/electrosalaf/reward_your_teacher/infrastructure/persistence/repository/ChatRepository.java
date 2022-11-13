package io.electrosalaf.reward_your_teacher.infrastructure.persistence.repository;

import io.electrosalaf.reward_your_teacher.domain.entities.message.ChatEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.Optional;

public interface ChatRepository extends JpaRepository<ChatEntity, Long> {

    Optional<ChatEntity> findChatEntitiesByCreatedAt(LocalDateTime localDateTime);
}
