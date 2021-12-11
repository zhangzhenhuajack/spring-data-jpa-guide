package com.example.jpa.quick.web;

import com.example.jpa.quick.core.BaseService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author jack
 */
public class BaseController<Entity,ID> {
    private final BaseService<Entity,ID> service;
    public BaseController(BaseService service) {
        this.service = service;
    }

    @GetMapping("/all")
    public Page<Entity> getUserInfos(Pageable pageRequest) {
        return service.findAll(pageRequest);
    }
    @GetMapping("/info/{id}")
    public Entity getUserInfo(@PathVariable("id") ID id) {
        return service.findById(id).get();
    }

    @PostMapping("/save")
    public Entity saveUserInfo(@RequestBody Entity entity) {
        return service.save(entity);
    }
}
