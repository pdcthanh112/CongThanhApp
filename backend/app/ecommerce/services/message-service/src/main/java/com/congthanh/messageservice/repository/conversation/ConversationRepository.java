package com.congthanh.messageservice.repository.conversation;

import com.congthanh.messageservice.model.entity.Conversation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ConversationRepository extends JpaRepository<Conversation, Long> {

    @Query("select c from Conversation c where c.toUser = :toUser and c.deliveryStatus in ('NOT_DELIVERED', 'DELIVERED') and c.fromUser = :fromUser")
    List<Conversation> findUnseenMessages(
            @Param("toUser") String toUser, @Param("fromUser") String fromUser);

    @Query(value = "select * from conversation where to_user = :toUser and delivery_status in ('NOT_DELIVERED', 'DELIVERED')", nativeQuery = true)
    List<Conversation> findUnseenMessagesCount(@Param("toUser") String toUser);

}
