package com.sky.service;

import com.sky.dto.UserLoginDTO;
import com.sky.entity.User;

/**
 * @projectName: sky-take-out
 * @package: com.sky.service
 * @className: UserService
 * @author: lwj
 * @description: TODO
 * @date: 2026/6/28 00:17
 * @version: 1.0
 */
public interface UserService {
    User wxlogin(UserLoginDTO userLoginDTO);
}
