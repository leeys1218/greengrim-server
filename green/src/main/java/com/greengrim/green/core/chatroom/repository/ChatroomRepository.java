package com.greengrim.green.core.chatroom.repository;

import com.greengrim.green.core.chatroom.Chatroom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatroomRepository extends JpaRepository<Chatroom, Long> {

}
