package com.springboot.vhrend.mapper;

import com.springboot.vhrend.model.Hr;
import org.apache.ibatis.annotations.Select;

public interface HrMapper {
    int deleteByPrimaryKey(Integer id);
    int insert(Hr record);
    int insertSelective(Hr record);
    Hr selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Hr record);
    int updateByPrimaryKey(Hr record);

    @Select("select * from hr where username=#{username}")
    Hr loadUserByUsername(String username);
}
