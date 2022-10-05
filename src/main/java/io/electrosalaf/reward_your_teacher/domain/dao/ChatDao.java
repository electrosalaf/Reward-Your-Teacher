package io.electrosalaf.reward_your_teacher.domain.dao;

import io.electrosalaf.reward_your_teacher.domain.entities.message.ChatEntity;

import java.time.LocalDateTime;

public interface ChatDao extends CrudDao<ChatEntity, Long> {

    ChatEntity findChatByCreatedAt(LocalDateTime localDateTime);
}
