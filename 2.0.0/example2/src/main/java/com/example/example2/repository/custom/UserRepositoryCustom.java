package com.example.example2.repository.custom;

import com.example.example2.entity.User;

import java.util.List;

/**
 * @author jack
 */
public interface UserRepositoryCustom {
    /**
     * 自定义一个查询方法，name的like查询，此处仅仅是演示例子，实际中直接用QueryMethod即可
     * @param firstName
     * @return
     */
    List<User> customerMethodNamesLike(String firstName);
}
