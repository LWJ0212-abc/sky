<<<<<<< HEAD
package com.sky.mapper;

import com.sky.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {
    @Select("select * from user where openid = #{openid}")
    User getByOpenid(String openid);


    void insert(User user);

    @Select("select * from user where id=#{id}")
    User getById(Long id);
}


=======
package com.sky.mapper;

import com.sky.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {
    @Select("select * from user where openid = #{openid}")
    User getByOpenid(String openid);


    void insert(User user);
}


>>>>>>> b9c976c421116e53dc22a6e710dbb89ad3856829
