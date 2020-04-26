package com.springboot.vhrend.mapper;
import com.springboot.vhrend.model.Position;
import org.apache.ibatis.annotations.Select;
import java.util.List;

public interface PositionMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Position record);

    int insertSelective(Position record);

    Position selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Position record);

    int updateByPrimaryKey(Position record);

    @Select("select * from position")
    List<Position> selectAllPosition();

    Integer deleteByIds(Integer[] ids);
}