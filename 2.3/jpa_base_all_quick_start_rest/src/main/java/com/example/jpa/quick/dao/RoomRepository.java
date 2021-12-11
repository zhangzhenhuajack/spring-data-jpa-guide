package com.example.jpa.quick.dao;

import com.example.jpa.quick.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author jack
 */
public interface RoomRepository extends JpaRepository<Room,Long> {
}
