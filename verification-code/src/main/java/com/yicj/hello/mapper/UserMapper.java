package com.yicj.hello.mapper;

import com.yicj.hello.model.User;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

@Component
public interface UserMapper {

    @Select("select * from users where username = #{username}")
    User findByUserName(@Param("username") String username) ;
}
