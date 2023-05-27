package com.example.springbootjwt.repository;

import com.example.springbootjwt.model.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface UserRepository {

    @Result(property = "passcode", column = "secret_key")
    @Result(property = "userID", column = "id")

    @Select("select * from users_tb where username like #{username} ")
    List<User> findUserByName(String name);
}
