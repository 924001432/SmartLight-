package com.example.light.mapper;




import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.light.entity.User;
import org.springframework.stereotype.Repository;

/**
 * 用户Mapper
 */
@Repository
public interface UserMapper extends BaseMapper<User> {
}