package com.example.jpa.quick.service.impl;

import com.example.jpa.quick.core.BaseServiceImpl;
import com.example.jpa.quick.dao.RoomRepository;
import com.example.jpa.quick.entity.Room;
import com.example.jpa.quick.service.RoomService;
import org.springframework.stereotype.Service;

/**
 * @author jack
 */
@Service
public class RoomServiceImpl extends BaseServiceImpl<Room,Long, RoomRepository> implements RoomService {
    public RoomServiceImpl(RoomRepository repository) {
        super(repository);
    }
}
