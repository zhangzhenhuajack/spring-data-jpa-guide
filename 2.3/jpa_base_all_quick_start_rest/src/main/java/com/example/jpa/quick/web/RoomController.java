package com.example.jpa.quick.web;

import com.example.jpa.quick.entity.Room;
import com.example.jpa.quick.service.RoomService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author jack， 利用基类 Controller 自带 room增删改查操作
 */
@RestController
@RequestMapping(path = "rooms")
public class RoomController extends BaseController<Room,Long>{
    public RoomController(RoomService service) {
        super(service);
    }
}
